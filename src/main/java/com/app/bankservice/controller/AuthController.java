package com.app.bankservice.controller;

import com.app.bankservice.entity.User;
import com.app.bankservice.model.JwtRequest;
import com.app.bankservice.model.JwtResponse;
import com.app.bankservice.model.UserDTO;
import com.app.bankservice.model.UserResponseDTO;
import com.app.bankservice.security.jwt.JwtTokenUtil;
import com.app.bankservice.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/dbservice/app")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final long expiresIn = jwtTokenUtil.getExpirationDateFromToken(token).getTime() - System.currentTimeMillis();

        return ResponseEntity.ok(new JwtResponse(token, expiresIn / 1000)); // expiresIn in seconds
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        User savedUser = userDetailsService.save(user);
        UserResponseDTO responseDTO = UserResponseDTO.fromUser(savedUser);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponseDTO> getByUserName(@PathVariable("username") String username ) throws Exception {
        UserResponseDTO responseDTO = userDetailsService.getUserByName(username);
        return ResponseEntity.ok(responseDTO);
    }

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
