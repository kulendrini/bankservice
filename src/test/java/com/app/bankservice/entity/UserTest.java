package com.app.bankservice.entity;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {

        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        Account account = new Account();
        account.setAccountId(1L);
        account.setAccountNumber("1234567890");
        account.setBalance(1000.0);

        BankCard bankCard = new BankCard();
        bankCard.setCardID(1L);
        bankCard.setCardNumber("1234567890123456");
        bankCard.setCreditLimit(5000.0);
        bankCard.setStatus("Active");
        bankCard.setExpiryDate(new Date(2025, 12, 31));  // Deprecated constructor used for example

        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setAmount(5000.0);
        loan.setInterestRate(5.0);
        loan.setStartDate(new Date(2023, 1, 1));
        loan.setDurationYears(5);

        user = new User();
        user.setId(1L);
        user.setUsername("johndoe");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setContactNo("555-1234");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setCreatedDate(new Date(2023, 1, 1));
        user.setUpdatedDate(new Date(2023, 2, 1));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        Set<Account> accounts = new HashSet<>();
        accounts.add(account);
        user.setAccounts(accounts);

        List<BankCard> bankCards = new ArrayList<>();
        bankCards.add(bankCard);
        user.setBankCards(bankCards);

        List<Loan> loans = new ArrayList<>();
        loans.add(loan);
        user.setLoans(loans);
    }

    @Test
    public void testUserFields() {
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("johndoe");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getContactNo()).isEqualTo("555-1234");
        assertThat(user.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(user.getAddress()).isEqualTo("123 Main St");
        assertThat(user.getCreatedDate()).isEqualTo(new Date(2023, 1, 1));
        assertThat(user.getUpdatedDate()).isEqualTo(new Date(2023, 2, 1));

        assertThat(user.getRoles()).hasSize(1);
        assertThat(user.getAccounts()).hasSize(1);
        assertThat(user.getBankCards()).hasSize(1);
        assertThat(user.getLoans()).hasSize(1);
    }

    @Test
    public void testUserMutators() {
        user.setUsername("janedoe");
        assertThat(user.getUsername()).isEqualTo("janedoe");

        Role newRole = new Role();
        newRole.setId(2L);
        newRole.setName("ADMIN");

        Set<Role> roles = new HashSet<>();
        roles.add(newRole);
        user.setRoles(roles);
        assertThat(user.getRoles()).hasSize(1);
        assertThat(user.getRoles().iterator().next().getName()).isEqualTo("ADMIN");

        Account newAccount = new Account();
        newAccount.setAccountId(2L);
        newAccount.setAccountNumber("0987654321");
        newAccount.setBalance(2000.0);

        Set<Account> accounts = new HashSet<>();
        accounts.add(newAccount);
        user.setAccounts(accounts);
        assertThat(user.getAccounts()).hasSize(1);
        assertThat(user.getAccounts().iterator().next().getAccountNumber()).isEqualTo("0987654321");

        BankCard newBankCard = new BankCard();
        newBankCard.setCardID(2L);
        newBankCard.setCardNumber("6543210987654321");
        newBankCard.setCreditLimit(10000.0);
        newBankCard.setStatus("Inactive");
        newBankCard.setExpiryDate(new Date(2026, 11, 30));  // Deprecated constructor used for example

        List<BankCard> bankCards = new ArrayList<>();
        bankCards.add(newBankCard);
        user.setBankCards(bankCards);
        assertThat(user.getBankCards()).hasSize(1);
        assertThat(user.getBankCards().get(0).getCardNumber()).isEqualTo("6543210987654321");

        Loan newLoan = new Loan();
        newLoan.setLoanId(2L);
        newLoan.setAmount(10000.0);
        newLoan.setInterestRate(6.0);
        newLoan.setStartDate(new Date(2024, 1, 1));
        newLoan.setDurationYears(5);

        List<Loan> loans = new ArrayList<>();
        loans.add(newLoan);
        user.setLoans(loans);
        assertThat(user.getLoans()).hasSize(1);
    }

    @Test
    public void testEqualsAndHashCode() {
        User anotherUser = new User();
        anotherUser.setId(1L);
        anotherUser.setUsername("johndoe");
        anotherUser.setFirstName("John");
        anotherUser.setLastName("Doe");
        anotherUser.setPassword("password123");
        anotherUser.setContactNo("555-1234");
        anotherUser.setEmail("johndoe@example.com");
        anotherUser.setAddress("123 Main St");
        anotherUser.setCreatedDate(new Date(2023, 1, 1));
        anotherUser.setUpdatedDate(new Date(2023, 2, 1));
        anotherUser.setRoles(user.getRoles());
        anotherUser.setAccounts(user.getAccounts());
        anotherUser.setBankCards(user.getBankCards());
        anotherUser.setLoans(user.getLoans());

        assertThat(user).isEqualTo(anotherUser);
        assertThat(user.hashCode()).isEqualTo(anotherUser.hashCode());
    }

    @Test
    public void testPrePersistAndPreUpdate() {
        User newUser = new User();
        newUser.onCreate();
        assertThat(newUser.getCreatedDate()).isNotNull();
        assertThat(newUser.getUpdatedDate()).isNotNull();
        newUser.onUpdate();
        assertThat(newUser.getUpdatedDate()).isNotNull();
    }
}