package com.admission.entity;



import com.admission.utils.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="t_mst_user")
@Data
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userName;

    private String name;

    @Column(length = 15)
    private String mobile;

    @Column(unique = true)
    private String email;

    private String password;

    private String profilePhoto;

    private boolean isAllowMultipleSession = false;
    private boolean isEnabled = true;
    private boolean isLocked = false;
    private boolean isLoggedIn = false;
    private int wrongLoginCount = 0;

    @Enumerated(EnumType.STRING)
    private Roles role;
	
    private Long objectId;

}
