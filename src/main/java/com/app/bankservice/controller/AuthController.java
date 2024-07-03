package com.app.bankservice.controller;

import com.app.bankservice.entity.User;
import com.app.bankservice.model.JwtRequest;
import com.app.bankservice.model.JwtResponse;
import com.app.bankservice.model.UserDTO;
import com.app.bankservice.model.UserResponseDTO;
import com.app.bankservice.security.jwt.JwtTokenUtil;
import com.app.bankservice.service.JwtUserDetailsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/dbservice/app")
@Validated
@CrossOrigin
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    /**
     * Authenticates the user and generates a JSON Web Token (JWT) for the authenticated user.
     *
     * This endpoint processes the authentication request by validating the provided username and password.
     * Upon successful authentication, it generates a JWT token for the user and returns it along with the
     * token's expiration time in seconds.
     *
     * @param authenticationRequest The request object containing the username and password for authentication.
     *                               Must be a valid {@link JwtRequest} object with non-empty username and password.
     * @return A {@link ResponseEntity} containing a {@link JwtResponse} with the generated JWT token and its
     *         expiration time in seconds, along with an HTTP status of {@code 200 OK} if the authentication is successful.
     *
     * @throws BadCredentialsException if the authentication fails due to incorrect username or password.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final long expiresIn = jwtTokenUtil.getExpirationDateFromToken(token).getTime() - System.currentTimeMillis();
        return ResponseEntity.ok(new JwtResponse(token, expiresIn / 1000)); // expiresIn in seconds
    }


    /**
     * Registers a new user by saving their details.
     *
     * This endpoint processes the user registration request by accepting a {@link UserDTO} object with user details.
     * Upon successful registration, it saves the user information and returns a {@link UserResponseDTO} object
     * representing the newly created user along with an HTTP status of {@code 201 Created}.
     *
     * @param user The user details to be registered. Must be a valid {@link UserDTO} object with non-null fields.
     * @return A {@link ResponseEntity} containing a {@link UserResponseDTO} with the details of the newly registered user
     *         and an HTTP status of {@code 201 Created} if the user is successfully registered.
     *
     * @throws MethodArgumentNotValidException if the {@link UserDTO} object contains invalid data.
     */
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO user) {
        User savedUser = userDetailsService.save(user);
        UserResponseDTO responseDTO = UserResponseDTO.fromUser(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    /**
     * Retrieves the user details for a specified username.
     *
     * This endpoint fetches the details of the user associated with the provided username. The username must not be empty;
     * otherwise, a validation error will be triggered.
     *
     * @param username The username of the user whose details are to be retrieved. Must not be empty.
     * @return A {@link ResponseEntity} containing a {@link UserResponseDTO} with the user details and an HTTP status
     *         of {@code 200 OK} if the user details are successfully retrieved.
     *
     * @throws IllegalArgumentException if the username is empty.
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponseDTO> getByUserName(@PathVariable("username") @NotEmpty(message = "Username cannot be empty") String username) {
        UserResponseDTO responseDTO = userDetailsService.getUserByName(username);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    /**
     * Authenticates the user with the provided username and password.
     *
     * This method uses the {@link AuthenticationManager} to authenticate the user by creating a {@link UsernamePasswordAuthenticationToken}
     * with the provided credentials and invoking the `authenticate` method on the {@link AuthenticationManager}.
     *
     * @param username The username of the user to be authenticated. Must not be null or empty.
     * @param password The password of the user to be authenticated. Must not be null or empty.
     *
     * @throws BadCredentialsException if the authentication fails due to incorrect username or password.
     * @throws IllegalArgumentException if the username or password is null or empty.
     */
    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
