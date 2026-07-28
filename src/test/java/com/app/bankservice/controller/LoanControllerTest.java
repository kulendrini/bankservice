package com.app.bankservice.controller;

import com.app.bankservice.model.LoanResponseDTO;
import com.app.bankservice.service.LoanService;
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
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new LoanController(loanService)).build();
    }

    @Test
    public void getLoan_ShouldReturn200Ok_WhenUsernameIsValid() throws Exception {
        String validUsername = "chamalka";
        LoanResponseDTO mockLoanResponse = new LoanResponseDTO();
        when(loanService.getLoanDetailsByUserName(validUsername)).thenReturn(mockLoanResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/loan/{username}", validUsername))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(loanService, times(1)).getLoanDetailsByUserName(validUsername);
    }

    @Test
    public void getLoan_ShouldReturnLoanResponse_WhenUsernameIsValid() throws Exception {
        String validUsername = "chamalka";
        LoanResponseDTO mockLoanResponse = new LoanResponseDTO();
        mockLoanResponse.setFirstName("Chamalka");
        mockLoanResponse.setLastName("Hettiarachchi");
        when(loanService.getLoanDetailsByUserName(validUsername)).thenReturn(mockLoanResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/loan/{username}", validUsername))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Chamalka"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Hettiarachchi"))
                .andDo(MockMvcResultHandlers.print());
        verify(loanService, times(1)).getLoanDetailsByUserName(validUsername);
    }

    @Test
    public void getLoan_ShouldReturn400BadRequest_WhenUsernameIsNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/dbservice/app/loan/{username}", (Object) null))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


}

