package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LoanPaymentTypeTest {

    private LoanPaymentType loanPaymentType;

    @BeforeEach
    public void setUp() {

        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setAmount(10000.0);
        loan.setInterestRate(5.0);
        loan.setDurationYears(3);
        loan.setStatus("Active");

        List<Loan> loans = new ArrayList<>();
        loans.add(loan);

        loanPaymentType = new LoanPaymentType();
        loanPaymentType.setPaymentTypeId(1L);
        loanPaymentType.setPaymentTypeName("EMI");
        loanPaymentType.setLoans(loans);
    }

    @Test
    public void testLoanPaymentTypeFields() {
        assertThat(loanPaymentType.getPaymentTypeId()).isEqualTo(1L);
        assertThat(loanPaymentType.getPaymentTypeName()).isEqualTo("EMI");
        assertThat(loanPaymentType.getLoans()).hasSize(1);
        assertThat(loanPaymentType.getLoans().get(0).getLoanId()).isEqualTo(1L);
    }

    @Test
    public void testLoanPaymentTypeMutators() {
        loanPaymentType.setPaymentTypeName("Lump Sum");
        assertThat(loanPaymentType.getPaymentTypeName()).isEqualTo("Lump Sum");

        Loan newLoan = new Loan();
        newLoan.setLoanId(2L);
        newLoan.setAmount(20000.0);
        newLoan.setInterestRate(4.5);
        newLoan.setDurationYears(5);
        newLoan.setStatus("Approved");

        List<Loan> loans = new ArrayList<>();
        loans.add(newLoan);
        loanPaymentType.setLoans(loans);
        assertThat(loanPaymentType.getLoans()).hasSize(1);
        assertThat(loanPaymentType.getLoans().get(0).getLoanId()).isEqualTo(2L);
    }

    @Test
    public void testEqualsAndHashCode() {
        LoanPaymentType anotherLoanPaymentType = new LoanPaymentType();
        anotherLoanPaymentType.setPaymentTypeId(1L);
        anotherLoanPaymentType.setPaymentTypeName("EMI");
        anotherLoanPaymentType.setLoans(loanPaymentType.getLoans());

        assertThat(loanPaymentType).isEqualTo(anotherLoanPaymentType);
        assertThat(loanPaymentType.hashCode()).isEqualTo(anotherLoanPaymentType.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "LoanPaymentType{" +
                "paymentTypeId=" + loanPaymentType.getPaymentTypeId() +
                ", paymentTypeName='" + loanPaymentType.getPaymentTypeName() + '\'' +
                ", loans=" + loanPaymentType.getLoans() +
                '}';
        assertThat(loanPaymentType.toString()).isEqualTo(expectedToString);
    }
}
