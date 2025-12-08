package com.admission.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name="students")
@Data

public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long studentId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String category;
	private Date dob;
	private String mobileNo;
	private String email;
	private Long userId;
	
}
