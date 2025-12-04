package com.admission.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="students")

public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

}
