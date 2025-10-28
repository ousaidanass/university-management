def SERVICES = ['config-server', 'api-gateway', 'student-service', 'address-service']

pipeline {
    agent any

    environment {
        GOOGLE_APPLICATION_CREDENTIALS=credentials('gcp-service-account')
        GOOGLE_CLOUD_PROJECT=credentials('gcp-project-id-university-management')
        DOCKER_IMAGE_TAG="latest"
        REGISTRY_URL="us-central1-docker.pkg.dev"
        REGISTRY_NAME="university-management"
        CLUSTER_NAME="university-management"
        CLUSTER_LOCATION="us-central1-a"
    }

    stages {

        stage("Build Maven Modules") {
            steps {
                echo 'Building all Maven modules...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage("Build docker images") {
            steps {
                script {
                    echo 'Building all docker images...'
                    for (service in SERVICES) {
                        sh "docker build -t ${service}:${DOCKER_IMAGE_TAG} ./${service}"
                    }
                }
            }
        }

        stage("Login to GCP Artifact Registry and Push Images") {
            steps {
                script {
                    echo 'Login to GCP Artifact Registry and Push Images...'
                    for (service in SERVICES) {
                        def fullImageName="${REGISTRY_URL}/${GOOGLE_CLOUD_PROJECT}/${REGISTRY_NAME}/${service}:${DOCKER_IMAGE_TAG}"
                        sh """
                            cat ${GOOGLE_APPLICATION_CREDENTIALS} | docker login -u _json_key --password-stdin https://${REGISTRY_URL}
                        """
                        sh "docker tag ${service}:${DOCKER_IMAGE_TAG} ${fullImageName}"

                        sh "docker push ${fullImageName}"

                        sh "docker image prune -f"
                    }
                }
            }
        }

         stage('Deploy to GKE') {
             steps {
                 script {
                     echo "Deployment started ..."

                     for (service in SERVICES) {
                         def imagePath = "${REGISTRY_URL}/${GOOGLE_CLOUD_PROJECT}/${REGISTRY_NAME}/${service}:${DOCKER_IMAGE_TAG}"
                         sh "sed -i 's|${service}:latest|${imagePath}|' deployment.yaml"
                     }

                     def manifests = ['deployment.yaml', 'service.yaml']
                     for (manifest in manifests) {
                         step([$class: 'KubernetesEngineBuilder',
                               projectId: env.GOOGLE_CLOUD_PROJECT,
                               clusterName: env.CLUSTER_NAME,
                               location: env.CLUSTER_LOCATION,
                               manifestPattern: manifest,
                               credentialsId: 'gcp-service-account-key',
                               verifyDeployments: true])
                    }
                     echo "Deployment Finished ..."
                 }
             }
         }
    }

}
