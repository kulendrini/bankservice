package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class RoleTest {

    private Role role;

    @BeforeEach
    public void setUp() {
        // Initialize Role object
        role = new Role();
        role.setId(1L);
        role.setName("USER");
    }

    @Test
    public void testRoleFields() {
        assertThat(role.getId()).isEqualTo(1L);
        assertThat(role.getName()).isEqualTo("USER");
    }

    @Test
    public void testRoleMutators() {
        role.setName("ADMIN");
        assertThat(role.getName()).isEqualTo("ADMIN");
    }

    @Test
    public void testGrantedAuthority() {
        assertThat(role.getAuthority()).isEqualTo("");
    }
}