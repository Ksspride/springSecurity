package com.example.identityservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sys_users")
@NoArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID  = -4301107841060380229L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "seq_sys_users", initialValue = 1000, allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name="ACTIVATION_STATUS")
    private Boolean isActive;
}
