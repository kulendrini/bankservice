package com.app.bankservice.controller;

import com.app.bankservice.model.AccountResponseDTO;
import com.app.bankservice.service.AccountService;
import com.app.bankservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService, transactionService)).build();
    }

    @Test
    public void getAccounts_ShouldReturn200Ok_WhenUsernameIsValid() throws Exception {
        String validUsername = "validUser";
        AccountResponseDTO mockAccountResponse = new AccountResponseDTO();
        mockAccountResponse.setFirstName("Chamalka");
        mockAccountResponse.setLastName("Hettiarachchi");
        when(accountService.getAccountDetailsByUsername(validUsername)).thenReturn(mockAccountResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/account/{username}", validUsername))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Chamalka"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Hettiarachchi"))
                .andDo(MockMvcResultHandlers.print());
        verify(accountService, times(1)).getAccountDetailsByUsername(validUsername);
    }


    @Test
    public void getAccounts_ShouldReturn400BadRequest_WhenUsernameIsInvalid() throws Exception {
        String invalidUsername = "invalidUser";
        when(accountService.getAccountDetailsByUsername(invalidUsername)).thenThrow(new IllegalArgumentException("Invalid username"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/account/{username}", invalidUsername))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
        verify(accountService, times(1)).getAccountDetailsByUsername(invalidUsername);
    }

    @Test
    public void getAccounts_ShouldReturn500InternalServerError_WhenUnexpectedErrorOccurs() throws Exception {
        String validUsername = "validUser";
        when(accountService.getAccountDetailsByUsername(validUsername)).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/account/{username}", validUsername))
                .andExpect(status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print());
        verify(accountService, times(1)).getAccountDetailsByUsername(validUsername);
    }

}
