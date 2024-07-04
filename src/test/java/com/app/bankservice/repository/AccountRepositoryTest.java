package com.app.bankservice.repository;

import com.app.bankservice.entity.Account;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setAccountNumber("1234567890");
        account.setBalance(1000.0);
        entityManager.persist(account);
        entityManager.flush();
    }

    @Test
    public void testFindByAccountNumber() {
        Account found = accountRepository.findByAccountNumber("1234567890");
        assertThat(found).isNotNull();
        assertThat(found.getAccountNumber()).isEqualTo("1234567890");
    }

    @Test
    public void testUpdateBalance() {
        int rowsUpdated = accountRepository.updateBalance(1500.0, "1234567890");

        assertThat(rowsUpdated).isEqualTo(1);

        Account updatedAccount = accountRepository.findByAccountNumber("1234567890");
        assertThat(updatedAccount.getBalance()).isEqualTo(1500.0);
    }
}