pipeline {
    agent any

    environment {
        GOOGLE_APPLICATION_CREDENTIALS=credentials('gcp-service-account	')
        GOOGLE_CLOUD_PROJECT=credentials('gcp-project-id-university-management')
        DOCKER_IMAGE_TAG="latest"
        REGISTRY_URL="us-central1-docker.pkg.dev"
        REGISTRY_NAME="university-management"
        def SERVICES = ['student-service', 'address-service']
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
    }

}
