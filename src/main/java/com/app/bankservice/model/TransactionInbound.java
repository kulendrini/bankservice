package com.app.bankservice.model;

import java.util.Date;

public class TransactionInbound {

    private String originAccountNo;
    private String destinationAccountNo;
    private String bank;
    private Double amount;
    private String comment;
    private Date transactionDate;
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
