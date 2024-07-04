package com.app.bankservice.controller;

import com.app.bankservice.entity.Role;
import com.app.bankservice.entity.User;
import com.app.bankservice.model.JwtRequest;
import com.app.bankservice.model.UserDTO;
import com.app.bankservice.model.UserResponseDTO;
import com.app.bankservice.security.jwt.JwtTokenUtil;
import com.app.bankservice.service.JwtUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtUserDetailsService userDetailsService;

    @InjectMocks
    private AuthController authController;

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFtYWxrYSIsImlhdCI6MTcyMDAzOTk4NiwiZXhwIjoxNzIwMDQxNzg2fQ.Gy-vWaCIBSiLeH5Viu2wmaiw_kkmpRW0cBVPcV_LSeXnCUvWJJhGSN9AOh96I4a4jhBG48fMYsOpPlUrFUdREA";

    UserDetails userDetails = mock(UserDetails.class);
    UserDTO userDTO = new UserDTO();
    Set<String> roles = new HashSet<>();
    User user = new User();
    Set<Role> roleSet = new HashSet<>();
    Role role = new Role();
    UserResponseDTO userResponseDTO = new UserResponseDTO();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        userDTO.setUsername("john_doe");
        userDTO.setPassword("SecureP@ssw0rd");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setContactNo("+1234567890");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setAddress("123 Elm Street, Springfield");

        roles.add("USER");
        roles.add("ADMIN");
        userDTO.setRoles(roles);

        role.setId(1L);
        role.setName("USER");
        roleSet.add(role);

        user.setUsername("john_doe");
        user.setPassword("SecureP@ssw0rd");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setContactNo("+1234567890");
        user.setEmail("john.doe@example.com");
        user.setAddress("123 Elm Street, Springfield");
        user.setRoles(roleSet);

        userResponseDTO.setId(1L);
        userResponseDTO.setUsername("chamalka");
        userResponseDTO.setFirstName("John");
        userResponseDTO.setLastName("Doe");
        userResponseDTO.setContactNo("123-456-7890");
        userResponseDTO.setEmail("john.doe@example.com");
        userResponseDTO.setAddress("123 Main St, Anytown, USA");
        userResponseDTO.setRoles(roles);

    }

    @Test
    public void createAuthenticationToken_ShouldReturn200OK_WhenValidRequest() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("chamalka", "chamalka123");
        when(userDetailsService.loadUserByUsername("chamalka")).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);
        when(jwtTokenUtil.getExpirationDateFromToken(token)).thenReturn(new Date(System.currentTimeMillis() + 3600 * 1000));
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/dbservice/app/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"chamalka\",\"password\":\"chamalka123\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expiresIn").value(3599))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void saveUser_ShouldReturn201Created_WhenValidRequest() throws Exception {
        User savedUser = new User();
        savedUser.setUsername("chamalka");
        savedUser.setPassword("hashedPassword");
        savedUser.setRoles(roleSet);
        UserResponseDTO responseDTO = UserResponseDTO.fromUser(user);
        when(userDetailsService.save(any(UserDTO.class))).thenReturn(savedUser);
        String requestPayload = "{\"username\":\"chamalka\",\"password\":\"newPassword\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"contactNo\":\"+1234567890\",\"email\":\"john.doe@example.com\",\"address\":\"123 Elm Street\",\"roles\":[\"USER\",\"ADMIN\"]}";
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/dbservice/app/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestPayload))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("chamalka"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles[0]").value("USER"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getByUserName_ShouldReturn200OK_WhenUserExists() throws Exception {
        when(userDetailsService.getUserByName("chamalka")).thenReturn(userResponseDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/user/chamalka", "chamalka"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("chamalka"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getByUserName_ShouldReturn404NotFound_WhenUserDoesNotExist() throws Exception {
        when(userDetailsService.getUserByName("nonExistingUser")).thenThrow(new UsernameNotFoundException("User not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{username}", "nonExistingUser"))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}
