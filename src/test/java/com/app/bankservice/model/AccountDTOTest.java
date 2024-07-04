package com.app.bankservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountDTOTest {

    private AccountDTO accountDTO;

    @BeforeEach
    public void setUp() {
        accountDTO = new AccountDTO();
    }

    @Test
    public void testGetAndSetAccountNumber() {
        String accountNumber = "1234567890";
        accountDTO.setAccountNumber(accountNumber);
        assertEquals(accountNumber, accountDTO.getAccountNumber());
    }

    @Test
    public void testGetAndSetBalance() {
        Double balance = 1000.0;
        accountDTO.setBalance(balance);
        assertEquals(balance, accountDTO.getBalance());
    }

    @Test
    public void testGetAndSetStatus() {
        String status = "Active";
        accountDTO.setStatus(status);
        assertEquals(status, accountDTO.getStatus());
    }

    @Test
    public void testGetAndSetCreatedDate() {
        Date createdDate = new Date();
        accountDTO.setCreatedDate(createdDate);
        assertEquals(createdDate, accountDTO.getCreatedDate());
    }

    @Test
    public void testGetAndSetAccountType() {
        String accountType = "Savings";
        accountDTO.setAccountType(accountType);
        assertEquals(accountType, accountDTO.getAccountType());
    }

    @Test
    public void testGetAndSetCurrencyType() {
        String currencyType = "USD";
        accountDTO.setCurrencyType(currencyType);
        assertEquals(currencyType, accountDTO.getCurrencyType());
    }

    @Test
    public void testGetAndSetBankCard() {
        BankCardDTO bankCardDTO = new BankCardDTO();
        bankCardDTO.setCardNumber("1111222233334444");
        accountDTO.setBankCard(bankCardDTO);
        assertEquals(bankCardDTO, accountDTO.getBankCard());
        assertEquals("1111222233334444", accountDTO.getBankCard().getCardNumber());
    }

    @Test
    public void testDefaultValues() {
        assertNull(accountDTO.getAccountNumber());
        assertNull(accountDTO.getBalance());
        assertNull(accountDTO.getStatus());
        assertNull(accountDTO.getCreatedDate());
        assertNull(accountDTO.getAccountType());
        assertNull(accountDTO.getCurrencyType());
        assertNull(accountDTO.getBankCard());
    }
}
