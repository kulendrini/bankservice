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
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
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
     * Authenticates the user and generates a JWT token.
     *
     * This endpoint takes a {@link JwtRequest} object containing the username and password, authenticates the user,
     * generates a JWT token if the authentication is successful, and returns a {@link JwtResponse} object containing
     * the token and its expiration time in seconds.
     *
     * @param authenticationRequest the authentication request containing the username and password
     * @return a {@link ResponseEntity} containing a {@link JwtResponse} with the JWT token and its expiration time if the request is successful:
     *         <ul>
     *             <li>200 OK: The authentication is successful, and the JWT token is included in the response body</li>
     *             <li>401 Unauthorized: The authentication fails due to invalid credentials</li>
     *             <li>500 Internal Server Error: An unexpected server error occurred during authentication</li>
     *         </ul>
     * @throws Exception if an error occurs during authentication
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final long expiresIn = jwtTokenUtil.getExpirationDateFromToken(token).getTime() - System.currentTimeMillis();

        return ResponseEntity.ok(new JwtResponse(token, expiresIn / 1000)); // expiresIn in seconds
    }


    /**
     * Registers a new user.
     *
     * This endpoint takes a {@link UserDTO} object containing the user details, saves the user to the database,
     * and returns a {@link UserResponseDTO} object with the saved user's details.
     *
     * @param user the user details to be registered, provided as a {@link UserDTO} object
     * @return a {@link ResponseEntity} containing a {@link UserResponseDTO} with the details of the registered user:
     *         <ul>
     *             <li>200 OK: The user was successfully registered, and the user's details are included in the response body</li>
     *             <li>400 Bad Request: The request body is invalid or incomplete</li>
     *             <li>500 Internal Server Error: An unexpected server error occurred during the user registration process</li>
     *         </ul>
     */
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO user) {
        try{
        User savedUser = userDetailsService.save(user);
        UserResponseDTO responseDTO = UserResponseDTO.fromUser(savedUser);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /**
     * Retrieves user details by username.
     *
     * This endpoint takes a username as a path variable, retrieves the user's details from the database,
     * and returns them in a {@link UserResponseDTO} object.
     *
     * @param username the username of the user whose details are to be retrieved
     * @return a {@link ResponseEntity} containing a {@link UserResponseDTO} with the details of the requested user:
     *         <ul>
     *             <li>200 OK: The user's details were successfully retrieved and are included in the response body</li>
     *             <li>400 Bad Request: The request is invalid or the username does not exist</li>
     *             <li>500 Internal Server Error: An unexpected server error occurred while retrieving the user's details</li>
     *         </ul>
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponseDTO> getByUserName(@PathVariable("username") @NotEmpty(message = "Username cannot be empty") String username ) {
        try {
        UserResponseDTO responseDTO = userDetailsService.getUserByName(username);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("Client error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected server error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /**
     * Authenticates a user based on their username and password.
     *
     * This method attempts to authenticate the user by creating an {@link UsernamePasswordAuthenticationToken}
     * with the provided username and password, and passing it to the {@link AuthenticationManager}.
     * If authentication fails, it throws an {@link Exception} with an appropriate message.
     *
     * @param username the username of the user trying to authenticate
     * @param password the password of the user trying to authenticate
     * @throws Exception if the user is disabled or the credentials are invalid
     * @throws DisabledException if the user account is disabled
     * @throws BadCredentialsException if the credentials are invalid
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
