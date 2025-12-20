package com.admission.entity;

import com.admission.utils.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="roles")
@Data
public class Role extends BaseEntity {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	private String roleCode;
	private String roleName;
	private String roleDesc;
	
	

}
