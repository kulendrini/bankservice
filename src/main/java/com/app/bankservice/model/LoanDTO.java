package com.app.bankservice.model;

import java.util.Date;

public class LoanDTO {

    private Double amount;
    private Double interestRate;
    private Integer durationYears;
    private String status;
    private Date startDate;
    private String loanType;
    private String loanPaymentType;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getDurationYears() {
        return durationYears;
    }

    public void setDurationYears(Integer durationYears) {
        this.durationYears = durationYears;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getLoanPaymentType() {
        return loanPaymentType;
    }

    public void setLoanPaymentType(String loanPaymentType) {
        this.loanPaymentType = loanPaymentType;
    }
}
