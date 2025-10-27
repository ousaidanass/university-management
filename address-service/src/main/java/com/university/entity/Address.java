package com.university.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "address", schema = "university")
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

}
