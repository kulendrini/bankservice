package com.app.bankservice.service;

import com.app.bankservice.entity.*;
import com.app.bankservice.model.AccountDTO;
import com.app.bankservice.model.AccountResponseDTO;
import com.app.bankservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testGetAccountDetailsByUsername_Success() {
//        String username = "testuser";
//        User user = new User();
//        user.setUsername(username);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//
//        AccountType accountType = new AccountType();
//        accountType.setAccountTypeName("Savings");
//
//        CurrencyType currencyType = new CurrencyType();
//        currencyType.setCurrencyName("USD");
//
//        BankCard bankCard = new BankCard();
//        bankCard.setCardNumber("1111222233334444");
//        bankCard.setCreditLimit(1000.0);
//        bankCard.setStatus("Active");
//        bankCard.setExpiryDate(new Date());
//        CardType cardType = new CardType();
//        cardType.setTypeName("Credit");
//        bankCard.setCardType(cardType);
//
//        Account account = new Account();
//        account.setAccountNumber("1234567890");
//        account.setBalance(1000.0);
//        account.setStatus("Active");
//        account.setCreatedDate(new Date());
//        account.setAccountType(accountType);
//        account.setCurrencyType(currencyType);
//        account.setBankCards(Collections.singletonList(bankCard));
//
//        Set<Account> accounts = new HashSet<>();
//        accounts.add(account);
//        user.setAccounts(accounts);
//
//        when(userRepository.findByUsername(username)).thenReturn(user);
//
//        AccountResponseDTO response = accountService.getAccountDetailsByUsername(username);
//
//        assertNotNull(response);
//        assertEquals("John", response.getFirstName());
//        assertEquals("Doe", response.getLastName());
//        assertEquals(1, response.getAccountDTOList().size());
//
//        AccountDTO accountDTO = response.getAccountDTOList().get(0);
//        assertEquals("1234567890", accountDTO.getAccountNumber());
//        assertEquals(1000.0, accountDTO.getBalance());
//        assertEquals("Active", accountDTO.getStatus());
//        assertEquals("Savings", accountDTO.getAccountType());
//        assertEquals("USD", accountDTO.getCurrencyType());
//        assertNotNull(accountDTO.getBankCard());
//        assertEquals("1111222233334444", accountDTO.getBankCard().getCardNumber());
//        assertEquals(1000.0, accountDTO.getBankCard().getCreditLimit());
//    }
//
//    @Test
//    public void testGetAccountDetailsByUsername_UserNotFound() {
//        String username = "unknownuser";
//
//        when(userRepository.findByUsername(username)).thenReturn(null);
//
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            accountService.getAccountDetailsByUsername(username);
//        });
//
//        assertEquals("User not found for username: unknownuser", exception.getMessage());
//    }
//
//    @Test
//    public void testGetAccountDetailsByUsername_NoAccounts() {
//        String username = "userNoAccounts";
//        User user = new User();
//        user.setUsername(username);
//        user.setFirstName("Jane");
//        user.setLastName("Smith");
//        user.setAccounts(Collections.emptySet());
//
//        when(userRepository.findByUsername(username)).thenReturn(user);
//
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            accountService.getAccountDetailsByUsername(username);
//        });
//
//        assertEquals("No accounts found for user: userNoAccounts", exception.getMessage());
//    }

    @Test
    public void testGetAccountDetailsByUsername_EmptyUsername() {
        String username = "";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccountDetailsByUsername(username);
        });

        assertEquals("Username cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testGetAccountDetailsByUsername_NullUsername() {
        String username = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.getAccountDetailsByUsername(username);
        });

        assertEquals("Username cannot be null or empty", exception.getMessage());
    }
}
