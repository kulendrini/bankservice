package com.app.bankservice.service;

import com.app.bankservice.entity.Role;
import com.app.bankservice.entity.User;
import com.app.bankservice.model.UserDTO;
import com.app.bankservice.model.UserResponseDTO;
import com.app.bankservice.repository.RoleRepository;
import com.app.bankservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }

    public User save(UserDTO userDTO) {
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setContactNo(userDTO.getContactNo());
        newUser.setEmail(userDTO.getEmail());
        newUser.setAddress(userDTO.getAddress());
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDTO.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
            roles.add(role);
        }
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    public UserResponseDTO getUserByName (String username) {
       User user = userRepository.findByUsername(username);
        return UserResponseDTO.fromUser(user);
    }

}
