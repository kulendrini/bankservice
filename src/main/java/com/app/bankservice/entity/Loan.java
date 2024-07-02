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

    public Loan() {
    }

    public Loan(Long loanId, Double amount, Double interestRate, Integer durationYears, String status, Date startDate) {
        this.loanId = loanId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.durationYears = durationYears;
        this.status = status;
        this.startDate = startDate;
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

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", durationYears=" + durationYears +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(loanId, loan.loanId) && Objects.equals(amount, loan.amount) && Objects.equals(interestRate, loan.interestRate) && Objects.equals(durationYears, loan.durationYears) && Objects.equals(status, loan.status) && Objects.equals(startDate, loan.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, amount, interestRate, durationYears, status, startDate);
    }
}
