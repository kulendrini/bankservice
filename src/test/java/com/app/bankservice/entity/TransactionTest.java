package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        // Initialize Transaction object
        transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setAmount(100.0);
        transaction.setDescription("Test Transaction");
        transaction.setDestinationAccountNo("0987654321");
        transaction.setBank("Bank A");
        transaction.setTransactionDate(new Date());

        Account account = new Account();
        account.setAccountId(1L);
        account.setAccountNumber("1234567890");
        account.setBalance(1000.0);
        transaction.setAccount(account);

        TransactionType transactionType = new TransactionType();
        transactionType.setTransactionTypeId(1L);
        transactionType.setTypeName("Transfer");
        transaction.setTransactionType(transactionType);
    }

    @Test
    public void testTransactionFields() {
        assertThat(transaction.getTransactionId()).isEqualTo(1L);
        assertThat(transaction.getAmount()).isEqualTo(100.0);
        assertThat(transaction.getDescription()).isEqualTo("Test Transaction");
        assertThat(transaction.getDestinationAccountNo()).isEqualTo("0987654321");
        assertThat(transaction.getBank()).isEqualTo("Bank A");
        assertThat(transaction.getTransactionDate()).isNotNull();
        assertThat(transaction.getAccount().getAccountId()).isEqualTo(1L);
        assertThat(transaction.getTransactionType().getTransactionTypeId()).isEqualTo(1L);
    }

    @Test
    public void testTransactionMutators() {
        transaction.setAmount(200.0);
        assertThat(transaction.getAmount()).isEqualTo(200.0);

        transaction.setDescription("Updated Transaction");
        assertThat(transaction.getDescription()).isEqualTo("Updated Transaction");

        transaction.setDestinationAccountNo("1234567890");
        assertThat(transaction.getDestinationAccountNo()).isEqualTo("1234567890");

        transaction.setBank("Bank B");
        assertThat(transaction.getBank()).isEqualTo("Bank B");
    }


}
