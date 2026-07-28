package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionTypeTest {

    private TransactionType transactionType;

    @BeforeEach
    public void setUp() {

        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setAmount(100.0);
        transaction.setDescription("Test Transaction");
        transaction.setDestinationAccountNo("0987654321");
        transaction.setBank("Bank A");
        transaction.setTransactionDate(new Date());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        transactionType = new TransactionType();
        transactionType.setTransactionTypeId(1L);
        transactionType.setTypeName("Transfer");
        transactionType.setTransactions(transactions);
    }

    @Test
    public void testTransactionTypeFields() {
        assertThat(transactionType.getTransactionTypeId()).isEqualTo(1L);
        assertThat(transactionType.getTypeName()).isEqualTo("Transfer");
        assertThat(transactionType.getTransactions()).hasSize(1);
        assertThat(transactionType.getTransactions().get(0).getTransactionId()).isEqualTo(1L);
    }

    @Test
    public void testTransactionTypeMutators() {
        transactionType.setTypeName("Withdrawal");
        assertThat(transactionType.getTypeName()).isEqualTo("Withdrawal");

        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionId(2L);
        newTransaction.setAmount(200.0);
        newTransaction.setDescription("New Transaction");
        newTransaction.setDestinationAccountNo("1234567890");
        newTransaction.setBank("Bank B");
        newTransaction.setTransactionDate(new Date());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(newTransaction);
        transactionType.setTransactions(transactions);
        assertThat(transactionType.getTransactions()).hasSize(1);
        assertThat(transactionType.getTransactions().get(0).getTransactionId()).isEqualTo(2L);
    }

    @Test
    public void testEqualsAndHashCode() {
        TransactionType anotherTransactionType = new TransactionType();
        anotherTransactionType.setTransactionTypeId(1L);
        anotherTransactionType.setTypeName("Transfer");
        anotherTransactionType.setTransactions(transactionType.getTransactions());

        assertThat(transactionType).isEqualTo(anotherTransactionType);
        assertThat(transactionType.hashCode()).isEqualTo(anotherTransactionType.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "TransactionType{transactionTypeId=1, typeName='Transfer', transactions=" + transactionType.getTransactions() + '}';
        assertThat(transactionType.toString()).isEqualTo(expectedToString);
    }
}