package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AccountTypeTest {

    private AccountType accountType;

    @BeforeEach
    public void setUp() {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setAccountId(1L);
        account.setAccountNumber("123456789");
        account.setBalance(1000.0);
        account.setStatus("Active");
        account.setCreatedDate(new java.util.Date());
        account.setAccountType(this.accountType);  // This avoids a circular reference issue
        accounts.add(account);

        accountType = new AccountType();
        accountType.setAccountTypeId(1L);
        accountType.setAccountTypeName("Savings");
        accountType.setInterestRate(2.5);
        accountType.setAccounts(accounts);
    }

    @Test
    public void testAccountTypeFields() {
        assertThat(accountType.getAccountTypeId()).isEqualTo(1L);
        assertThat(accountType.getAccountTypeName()).isEqualTo("Savings");
        assertThat(accountType.getInterestRate()).isEqualTo(2.5);
        assertThat(accountType.getAccounts()).isNotEmpty();
        assertThat(accountType.getAccounts().get(0).getAccountNumber()).isEqualTo("123456789");
    }

    @Test
    public void testAccountTypeMutators() {
        accountType.setAccountTypeName("Checking");
        assertThat(accountType.getAccountTypeName()).isEqualTo("Checking");

        accountType.setInterestRate(3.0);
        assertThat(accountType.getInterestRate()).isEqualTo(3.0);

        List<Account> newAccounts = new ArrayList<>();
        Account newAccount = new Account();
        newAccount.setAccountId(2L);
        newAccount.setAccountNumber("987654321");
        newAccount.setBalance(2000.0);
        newAccount.setStatus("Inactive");
        newAccount.setCreatedDate(new java.util.Date());
        newAccounts.add(newAccount);

        accountType.setAccounts(newAccounts);
        assertThat(accountType.getAccounts()).hasSize(1);
        assertThat(accountType.getAccounts().get(0).getAccountNumber()).isEqualTo("987654321");
    }

    @Test
    public void testEqualsAndHashCode() {
        AccountType anotherAccountType = new AccountType();
        anotherAccountType.setAccountTypeId(1L);
        anotherAccountType.setAccountTypeName("Savings");
        anotherAccountType.setInterestRate(2.5);
        anotherAccountType.setAccounts(accountType.getAccounts());

        assertThat(accountType).isEqualTo(anotherAccountType);
        assertThat(accountType.hashCode()).isEqualTo(anotherAccountType.hashCode());
    }

    @Test
    public void testDefaultConstructor() {
        AccountType defaultAccountType = new AccountType();
        assertThat(defaultAccountType.getAccountTypeId()).isNull();
        assertThat(defaultAccountType.getAccountTypeName()).isNull();
        assertThat(defaultAccountType.getInterestRate()).isEqualTo(0.0);
        assertThat(defaultAccountType.getAccounts()).isNull();
    }
}

