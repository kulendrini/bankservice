package com.app.bankservice.entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;
    private Double amount;
    private Double interestRate;
    private Integer durationYears;
    private String status;
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @ManyToOne
    @JoinColumn(name = "loan_payment_type_id")
    private LoanPaymentType loanPaymentType;

    public Loan() {
    }

    public Loan(Long loanId, Double amount, Double interestRate, Integer durationYears, String status, Date startDate, LoanType loanType, LoanPaymentType loanPaymentType) {
        this.loanId = loanId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.durationYears = durationYears;
        this.status = status;
        this.startDate = startDate;
        this.loanType = loanType;
        this.loanPaymentType = loanPaymentType;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

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

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public LoanPaymentType getLoanPaymentType() {
        return loanPaymentType;
    }

    public void setLoanPaymentType(LoanPaymentType loanPaymentType) {
        this.loanPaymentType = loanPaymentType;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", durationYears=" + durationYears +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", loanType=" + loanType +
                ", loanPaymentType=" + loanPaymentType +
                '}';
    }



}
