package com.app.bankservice.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "loan_payment_type")
public class LoanPaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentTypeId;
    private String paymentTypeName;

    @OneToMany(targetEntity = Loan.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_payment_type_id", referencedColumnName = "paymentTypeId")
    private List<Loan> loans;

    public LoanPaymentType() {
    }

    public LoanPaymentType(Long paymentTypeId, String paymentTypeName, List<Loan> loans) {
        this.paymentTypeId = paymentTypeId;
        this.paymentTypeName = paymentTypeName;
        this.loans = loans;
    }

    public Long getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "LoanPaymentType{" +
                "paymentTypeId=" + paymentTypeId +
                ", paymentTypeName='" + paymentTypeName + '\'' +
                ", loans=" + loans +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPaymentType that = (LoanPaymentType) o;
        return Objects.equals(paymentTypeId, that.paymentTypeId) && Objects.equals(paymentTypeName, that.paymentTypeName) && Objects.equals(loans, that.loans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentTypeId, paymentTypeName, loans);
    }
}
