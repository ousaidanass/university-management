package com.university.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student", schema = "university")
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "address_id")
	private Long addressId;
	
}
