package com.app.bankservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class TransactionResponseDTO {

    private String accountNumber;
    private Double balance;
    private String status;
    private Date createdDate;
    private String accountType;
    private String currencyType;
    @JsonProperty("transaction")
    private List<TransactionDTO> transactionDTOS;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public List<TransactionDTO> getTransactionDTOS() {
        return transactionDTOS;
    }

    public void setTransactionDTOS(List<TransactionDTO> transactionDTOS) {
        this.transactionDTOS = transactionDTOS;
    }
}
