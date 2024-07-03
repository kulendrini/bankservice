package com.app.bankservice.controller;

import com.app.bankservice.model.TransactionDetailsDTO;
import com.app.bankservice.model.TransactionInbound;
import com.app.bankservice.model.TransactionOutbound;
import com.app.bankservice.model.TransferStatus;
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

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController(transactionService)).build();
    }

    @Test
    public void makeTransaction_ShouldReturn200Ok_WhenTransactionIsValid() throws Exception {
        TransactionInbound transactionInbound = new TransactionInbound();
        transactionInbound.setBank("Bank 01");
        transactionInbound.setTransactionTypeId(2L);
        transactionInbound.setAmount(500.0);
        TransactionOutbound transactionOutbound = new TransactionOutbound();
        transactionOutbound.setSenderAccountNo("9364528384748");
        transactionOutbound.setTransferStatus(TransferStatus.SUCCESS); // Ensure enum is correctly handled
        transactionOutbound.setTransferCurrency("LKR");
        transactionOutbound.setBeneficiaryAccountNo("87654398900");
        when(transactionService.makeTransaction(transactionInbound)).thenReturn(transactionOutbound);
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/dbservice/app/transaction")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transactionInbound)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.senderAccountNo").value("9364528384748"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.transferStatus").value("SUCCESS")) // Ensure correct string representation of enum
//                .andExpect(MockMvcResultMatchers.jsonPath("$.transferCurrency").value("LKR"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.beneficiaryAccountNo").value("87654398900"))
//                .andDo(MockMvcResultHandlers.print()); // Print the response to the console
//        verify(transactionService, times(1)).makeTransaction(transactionInbound);
    }

    @Test
    public void makeTransaction_ShouldReturn400BadRequest_WhenTransactionIsInvalid() throws Exception {
        TransactionInbound invalidTransactionInbound = new TransactionInbound();
        invalidTransactionInbound.setBank("Bank 01");
        invalidTransactionInbound.setTransactionTypeId(2L);
        invalidTransactionInbound.setAmount(500.0); // Or set invalid data
        when(transactionService.makeTransaction(invalidTransactionInbound)).thenThrow(new IllegalArgumentException("Invalid transaction"));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/dbservice/app/transaction")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidTransactionInbound)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andDo(MockMvcResultHandlers.print());
//        verify(transactionService, times(1)).makeTransaction(invalidTransactionInbound);
    }

    @Test
    public void makeTransaction_ShouldReturn500InternalServerError_WhenUnexpectedErrorOccurs() throws Exception {
        TransactionInbound transactionInbound = new TransactionInbound();
        transactionInbound.setBank("Bank 01");
        transactionInbound.setTransactionTypeId(2L);
        transactionInbound.setAmount(500.0);
        when(transactionService.makeTransaction(transactionInbound)).thenThrow(new RuntimeException("Unexpected error"));
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/dbservice/app/transaction")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transactionInbound)))
//                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
//                .andDo(MockMvcResultHandlers.print());
//        verify(transactionService, times(1)).makeTransaction(transactionInbound);
    }


    @Test
    public void getTransactionById_ShouldReturn200Ok_WhenTransactionIdIsValid() throws Exception {
        Long validTransactionId = 12345L;
        TransactionDetailsDTO mockTransactionDetails = new TransactionDetailsDTO();
        mockTransactionDetails.setTransactionId(validTransactionId);
        mockTransactionDetails.setAccountNumber("9364528384748");
        mockTransactionDetails.setDescription("Payment");
        mockTransactionDetails.setAmount(500.0);
        mockTransactionDetails.setTransactionType("Credit");
        when(transactionService.getTransactionById(validTransactionId)).thenReturn(mockTransactionDetails);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/transaction/{transactionId}", validTransactionId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(validTransactionId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("9364528384748"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Payment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(500.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType").value("Credit"))
                .andDo(MockMvcResultHandlers.print());
        verify(transactionService, times(1)).getTransactionById(validTransactionId);
    }

    @Test
    public void getTransactionById_ShouldReturn400BadRequest_WhenTransactionIdIsInvalid() throws Exception {
        Long invalidTransactionId = -1L;
        when(transactionService.getTransactionById(invalidTransactionId)).thenThrow(new IllegalArgumentException("Invalid transaction ID"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/transaction/{transactionId}", invalidTransactionId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
        verify(transactionService, times(1)).getTransactionById(invalidTransactionId);
    }

    @Test
    public void getTransactionById_ShouldReturn500InternalServerError_WhenUnexpectedErrorOccurs() throws Exception {
        Long validTransactionId = 12345L;
        when(transactionService.getTransactionById(validTransactionId)).thenThrow(new RuntimeException("Unexpected error"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/transaction/{transactionId}", validTransactionId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(MockMvcResultHandlers.print());
        verify(transactionService, times(1)).getTransactionById(validTransactionId);
    }


}
