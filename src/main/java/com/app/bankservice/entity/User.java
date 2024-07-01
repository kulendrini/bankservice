package com.app.bankservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;
    private String contactNo;
    private String email;
    private String address;
    private String password;
    private Date registrationDate;
    private boolean isActive;

        @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles = new HashSet<>();
}
