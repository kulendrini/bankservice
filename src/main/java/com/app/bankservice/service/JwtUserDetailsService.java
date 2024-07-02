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

    /**
     * Loads a user based on the provided username for authentication.
     * <p>
     * This method fetches a {@link User} entity from the repository using the specified username,
     * and then converts it to a {@link org.springframework.security.core.userdetails.User} object
     * which Spring Security uses for authentication and authorization. If no user is found with
     * the given username, a {@link UsernameNotFoundException} is thrown.
     * </p>
     *
     * @param username The username of the user to be retrieved. This value must be provided and
     *                 cannot be {@code null}.
     * @return A {@link UserDetails} object containing the user’s details and authorities.
     * @throws UsernameNotFoundException if the user with the provided username is not found.
     * @see User
     * @see org.springframework.security.core.userdetails.User
     * @see UserRepository#findByUsername(String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }


    /**
     * Creates and saves a new user based on the provided {@link UserDTO} data.
     * <p>
     * This method performs the following steps to save a new user:
     * <ul>
     * <li>Creates a new {@link User} entity from the provided {@link UserDTO} data.</li>
     * <li>Encodes the user’s password using the {@link PasswordEncoder}.</li>
     * <li>Sets the user's roles, creating new roles if they do not already exist.</li>
     * <li>Saves the new user to the repository.</li>
     * </ul>
     * <p>
     * The method includes error handling to ensure that user roles are correctly assigned and that the user entity is properly saved.
     * </p>
     *
     * @param userDTO The data required to create a new user. This cannot be null and must contain valid user details.
     * @return The newly created {@link User} entity.
     * @throws IllegalArgumentException if the {@link UserDTO} object is null or contains invalid data (e.g., missing required fields).
     * @throws RuntimeException if there is an error saving the user or creating roles.
     * @see UserDTO
     * @see User
     * @see Role
     */
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


    /**
     * Retrieves user details based on the provided username and returns a {@link UserResponseDTO}.
     * <p>
     * This method fetches a {@link User} entity from the repository using the specified username
     * and then converts it to a {@link UserResponseDTO} using the {@link UserResponseDTO#fromUser(User)} method.
     * If the user is not found, it will return a {@code null} response.
     * </p>
     *
     * @param username The username of the user whose details are to be retrieved. This cannot be null or empty.
     * @return A {@link UserResponseDTO} containing the user's details, or {@code null} if the user is not found.
     * @throws IllegalArgumentException if the provided username is null or empty.
     * @throws RuntimeException if there is an error retrieving the user from the repository.
     * @see User
     * @see UserResponseDTO
     * @see UserResponseDTO#fromUser(User)
     */
    public UserResponseDTO getUserByName (String username) {
       User user = userRepository.findByUsername(username);
        return UserResponseDTO.fromUser(user);
    }

}
