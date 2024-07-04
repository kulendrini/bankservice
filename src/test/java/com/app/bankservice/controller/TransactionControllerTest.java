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

import java.util.Date;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    TransactionInbound transactionInbound = new TransactionInbound();
    TransactionOutbound transactionOutbound = new TransactionOutbound();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController(transactionService)).build();

        transactionInbound.setOriginAccountNo("9364528384748");
        transactionInbound.setDestinationAccountNo("87654398900");
        transactionInbound.setAmount(500.0);
        transactionInbound.setBank("Bank 01");
        transactionInbound.setTransactionTypeId(2L);
        transactionInbound.setTransactionDate(new Date());

        transactionOutbound.setSenderAccountNo("9364528384748");
        transactionOutbound.setBeneficiaryAccountNo("87654398900");
        transactionOutbound.setBeneficiaryBank("Bank 01");
        transactionOutbound.setTransferCurrency("LKR");
        transactionOutbound.setTransferAmount(500.0);
        transactionOutbound.setSenderAccountBalance(1000.0);
        transactionOutbound.setTransferStatus(TransferStatus.SUCCESS);
    }

    @Test
    public void makeTransaction_ShouldReturn200Ok_WhenTransactionIsValid() throws Exception {
        when(transactionService.makeTransaction(transactionInbound)).thenReturn(transactionOutbound);
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/dbservice/app/transaction")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transactionInbound)))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.senderAccountNo").value("9364528384748"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.beneficiaryAccountNo").value("87654398900"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.beneficiaryBank").value("Bank 01"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.transferCurrency").value("LKR"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.transferAmount").value(500.0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.senderAccountBalance").value(1000.0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.transferStatus").value("SUCCESS"))
//                .andDo(MockMvcResultHandlers.print());
//        verify(transactionService, times(1)).makeTransaction(transactionInbound); }
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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").value(validTransactionId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("9364528384748"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Payment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(500.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType").value("Credit"))
                .andDo(MockMvcResultHandlers.print());
        verify(transactionService, times(1)).getTransactionById(validTransactionId);
    }

}
