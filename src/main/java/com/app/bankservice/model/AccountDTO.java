package com.app.bankservice.model;

import java.util.Date;

public class AccountDTO {

    private String accountNumber;
    private Double balance;
    private String status;
    private Date createdDate;
    private String accountType;
    private String currencyType;
    private BankCardDTO bankCard;

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

    public BankCardDTO getBankCard() {
        return bankCard;
    }

    public void setBankCard(BankCardDTO bankCard) {
        this.bankCard = bankCard;
    }
}
