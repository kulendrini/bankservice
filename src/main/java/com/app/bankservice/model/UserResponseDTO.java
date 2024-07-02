package com.app.bankservice.model;

import com.app.bankservice.entity.Role;
import com.app.bankservice.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserResponseDTO {

    private Long id;
    private String username;
    private Set<String> roles;

    public UserResponseDTO(Long id, String username, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public static UserResponseDTO fromUser(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        return new UserResponseDTO(user.getId(), user.getUsername(), roleNames);
    }
}
