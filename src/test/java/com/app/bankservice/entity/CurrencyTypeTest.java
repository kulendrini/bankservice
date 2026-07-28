package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrencyTypeTest {

    private CurrencyType currencyType;

    @BeforeEach
    public void setUp() {
        Account account = new Account();
        account.setAccountId(1L);
        account.setAccountNumber("1234567890");
        account.setBalance(1000.0);
        account.setCurrencyType(new CurrencyType(1L, "USD", new ArrayList<>()));

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        currencyType = new CurrencyType();
        currencyType.setCurrencyTypeId(1L);
        currencyType.setCurrencyName("USD");
        currencyType.setAccounts(accounts);
    }

    @Test
    public void testCurrencyTypeFields() {
        assertThat(currencyType.getCurrencyTypeId()).isEqualTo(1L);
        assertThat(currencyType.getCurrencyName()).isEqualTo("USD");
        assertThat(currencyType.getAccounts()).hasSize(1);
        assertThat(currencyType.getAccounts().get(0).getAccountId()).isEqualTo(1L);
    }

    @Test
    public void testCurrencyTypeMutators() {
        currencyType.setCurrencyName("EUR");
        assertThat(currencyType.getCurrencyName()).isEqualTo("EUR");

        Account newAccount = new Account();
        newAccount.setAccountId(2L);
        newAccount.setAccountNumber("0987654321");
        newAccount.setBalance(2000.0);
        newAccount.setCurrencyType(new CurrencyType(2L, "EUR", new ArrayList<>()));

        List<Account> accounts = new ArrayList<>();
        accounts.add(newAccount);
        currencyType.setAccounts(accounts);
        assertThat(currencyType.getAccounts()).hasSize(1);
        assertThat(currencyType.getAccounts().get(0).getAccountId()).isEqualTo(2L);
    }

    @Test
    public void testEqualsAndHashCode() {
        CurrencyType anotherCurrencyType = new CurrencyType();
        anotherCurrencyType.setCurrencyTypeId(1L);
        anotherCurrencyType.setCurrencyName("USD");
        anotherCurrencyType.setAccounts(currencyType.getAccounts());

        assertThat(currencyType).isEqualTo(anotherCurrencyType);
        assertThat(currencyType.hashCode()).isEqualTo(anotherCurrencyType.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "CurrencyType{" +
                "currencyTypeId=" + currencyType.getCurrencyTypeId() +
                ", currencyName='" + currencyType.getCurrencyName() + '\'' +
                ", accounts=" + currencyType.getAccounts() +
                '}';
        assertThat(currencyType.toString()).isEqualTo(expectedToString);
    }
}
