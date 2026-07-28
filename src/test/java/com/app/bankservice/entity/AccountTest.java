package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest {

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setAccountId(1L);
        account.setAccountNumber("123456789");
        account.setBalance(1000.0);
        account.setStatus("Active");
        account.setCreatedDate(new Date());

        AccountType accountType = new AccountType();
        accountType.setAccountTypeId(1L);
        accountType.setAccountTypeName("Savings");
        account.setAccountType(accountType);

        CurrencyType currencyType = new CurrencyType();
        currencyType.setCurrencyTypeId(1L);
        currencyType.setCurrencyName("USD");
        account.setCurrencyType(currencyType);

        Set<User> users = new HashSet<>();
        User user = new User();
        user.setId(1L);
        users.add(user);
        account.setUsers(users);

        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);
        transactions.add(transaction);
        account.setTransactions(transactions);

        List<BankCard> bankCards = new ArrayList<>();
        BankCard bankCard = new BankCard();
        bankCard.setCardID(1L);
        bankCards.add(bankCard);
        account.setBankCards(bankCards);
    }

    @Test
    public void testAccountFields() {
        assertThat(account.getAccountId()).isEqualTo(1L);
        assertThat(account.getAccountNumber()).isEqualTo("123456789");
        assertThat(account.getBalance()).isEqualTo(1000.0);
        assertThat(account.getStatus()).isEqualTo("Active");
        assertThat(account.getCreatedDate()).isNotNull();

        assertThat(account.getAccountType()).isNotNull();
        assertThat(account.getAccountType().getAccountTypeId()).isEqualTo(1L);
        assertThat(account.getAccountType().getAccountTypeName()).isEqualTo("Savings");

        assertThat(account.getCurrencyType()).isNotNull();
        assertThat(account.getCurrencyType().getCurrencyTypeId()).isEqualTo(1L);
        assertThat(account.getCurrencyType().getCurrencyName()).isEqualTo("USD");

        assertThat(account.getUsers()).isNotEmpty();
        assertThat(account.getUsers().iterator().next().getId()).isEqualTo(1L);

        assertThat(account.getTransactions()).isNotEmpty();
        assertThat(account.getTransactions().iterator().next().getTransactionId()).isEqualTo(1L);

        assertThat(account.getBankCards()).isNotEmpty();
        assertThat(account.getBankCards().iterator().next().getCardID()).isEqualTo(1L);
    }

    @Test
    public void testAccountMutators() {
        AccountType newAccountType = new AccountType();
        newAccountType.setAccountTypeId(2L);
        newAccountType.setAccountTypeName("Checking");
        account.setAccountType(newAccountType);
        assertThat(account.getAccountType().getAccountTypeName()).isEqualTo("Checking");

        CurrencyType newCurrencyType = new CurrencyType();
        newCurrencyType.setCurrencyTypeId(2L);
        newCurrencyType.setCurrencyName("EUR");
        account.setCurrencyType(newCurrencyType);
        assertThat(account.getCurrencyType().getCurrencyName()).isEqualTo("EUR");

        account.setBalance(2000.0);
        assertThat(account.getBalance()).isEqualTo(2000.0);
    }

    @Test
    public void testAccountAssociations() {
        assertThat(account.getUsers()).hasSize(1);
        assertThat(account.getTransactions()).hasSize(1);
        assertThat(account.getBankCards()).hasSize(1);
    }
}
