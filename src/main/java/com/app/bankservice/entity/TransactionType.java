package com.app.bankservice.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "transaction_type")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionTypeId;
    private String typeName;

    @OneToMany(targetEntity = Transaction.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_type_id", referencedColumnName = "transactionTypeId")
    private List<Transaction> transactions;

    public TransactionType() {
    }

    public TransactionType(Long transactionTypeId, String typeName, List<Transaction> transactions) {
        this.transactionTypeId = transactionTypeId;
        this.typeName = typeName;
        this.transactions = transactions;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "transactionTypeId=" + transactionTypeId +
                ", typeName='" + typeName + '\'' +
                ", transactions=" + transactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionType that = (TransactionType) o;
        return Objects.equals(transactionTypeId, that.transactionTypeId) && Objects.equals(typeName, that.typeName) && Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionTypeId, typeName, transactions);
    }
}
