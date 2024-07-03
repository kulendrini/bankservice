package com.app.bankservice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class TransactionInbound {

    @NotEmpty(message = "Origin account number cannot be empty")
    @Size(min = 1, max = 20, message = "Origin account number must be between 1 and 20 characters")
    private String originAccountNo;

    @NotEmpty(message = "Destination account number cannot be empty")
    @Size(min = 1, max = 20, message = "Destination account number must be between 1 and 20 characters")
    private String destinationAccountNo;

    @NotEmpty(message = "Bank cannot be empty")
    @Size(min = 1, max = 50, message = "Bank name must be between 1 and 50 characters")
    private String bank;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @Size(max = 255, message = "Comment must be less than 255 characters")
    private String comment;

    @NotNull(message = "Transaction date cannot be null")
    private Date transactionDate;

    @NotNull(message = "Transaction type ID cannot be null")
    private Long transactionTypeId;

    public String getOriginAccountNo() {
        return originAccountNo;
    }

    public void setOriginAccountNo(String originAccountNo) {
        this.originAccountNo = originAccountNo;
    }

    public String getDestinationAccountNo() {
        return destinationAccountNo;
    }

    public void setDestinationAccountNo(String destinationAccountNo) {
        this.destinationAccountNo = destinationAccountNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }
}
