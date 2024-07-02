package com.app.bankservice.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "loan_type")
public class LoanType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanTypeId;
    private String typeName;
    private Integer maxDurationYears;
    private Double interestRate;

    @OneToMany(targetEntity = Loan.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_type_id", referencedColumnName = "loanTypeId")
    private List<Loan> loans;

    public LoanType() {
    }

    public LoanType(Long loanTypeId, String typeName, Integer maxDurationYears, Double interestRate, List<Loan> loans) {
        this.loanTypeId = loanTypeId;
        this.typeName = typeName;
        this.maxDurationYears = maxDurationYears;
        this.interestRate = interestRate;
        this.loans = loans;
    }

    public Long getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(Long loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getMaxDurationYears() {
        return maxDurationYears;
    }

    public void setMaxDurationYears(Integer maxDurationYears) {
        this.maxDurationYears = maxDurationYears;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "LoanType{" +
                "loanTypeId=" + loanTypeId +
                ", typeName='" + typeName + '\'' +
                ", maxDurationYears=" + maxDurationYears +
                ", interestRate=" + interestRate +
                ", loans=" + loans +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanType loanType = (LoanType) o;
        return Objects.equals(loanTypeId, loanType.loanTypeId) && Objects.equals(typeName, loanType.typeName) && Objects.equals(maxDurationYears, loanType.maxDurationYears) && Objects.equals(interestRate, loanType.interestRate) && Objects.equals(loans, loanType.loans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanTypeId, typeName, maxDurationYears, interestRate, loans);
    }
}
