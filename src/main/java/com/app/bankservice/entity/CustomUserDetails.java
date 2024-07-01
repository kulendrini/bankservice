package com.app.bankservice.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class CustomUserDetails implements UserDetails {

    private String firstName;
    private String password;
    private Set<GrantedAuthority> authorities = new HashSet<>();
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;


}
