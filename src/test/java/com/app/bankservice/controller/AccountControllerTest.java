
package com.app.bankservice.controller;

import com.app.bankservice.model.AccountResponseDTO;
import com.app.bankservice.model.TransactionDTO;
import com.app.bankservice.model.TransactionResponseDTO;
import com.app.bankservice.service.AccountService;
import com.app.bankservice.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

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

    @Autowired
    private ObjectMapper objectMapper;

    TransactionResponseDTO responseDTO = new TransactionResponseDTO();
    TransactionDTO transactionDTO = new TransactionDTO();

    String accountNumber = "1234567890";
    int from = 0;
    int to = 10;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService, transactionService)).build();

        responseDTO.setAccountNumber(accountNumber);
        responseDTO.setBalance(1000.0);
        responseDTO.setStatus("Success");
        responseDTO.setCreatedDate(new Date());
        responseDTO.setAccountType("Savings");
        responseDTO.setCurrencyType("USD");

        transactionDTO.setAmount(500.0);
        transactionDTO.setTransactionType("Credit");
        transactionDTO.setTransactionDate(new Date());
        transactionDTO.setDescription("Test Transaction");
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
    public void testGetAccountTransactions() throws Exception {
        responseDTO.setTransactionDTOS(List.of(transactionDTO));
        when(transactionService.getTransactionDetailsByAccountNo(accountNumber, from, to)).thenReturn(responseDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/account/{accountNumber}/transactions", accountNumber)
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(to))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountType").value("Savings"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currencyType").value("USD"));
    }
}
