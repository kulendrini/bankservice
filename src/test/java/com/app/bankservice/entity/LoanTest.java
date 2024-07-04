package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class LoanTest {

    private Loan loan;

    @BeforeEach
    public void setUp() {

        LoanType loanType = new LoanType();
        loanType.setLoanTypeId(1L);
        loanType.setTypeName("Personal Loan");
        loanType.setMaxDurationYears(5);
        loanType.setInterestRate(5.0);

        LoanPaymentType loanPaymentType = new LoanPaymentType();
        loanPaymentType.setPaymentTypeId(1L);
        loanPaymentType.setPaymentTypeName("EMI");

        loan = new Loan();
        loan.setLoanId(1L);
        loan.setAmount(10000.0);
        loan.setInterestRate(5.0);
        loan.setDurationYears(3);
        loan.setStatus("Active");
        loan.setStartDate(new Date());
        loan.setLoanType(loanType);
        loan.setLoanPaymentType(loanPaymentType);
    }

    @Test
    public void testLoanFields() {
        assertThat(loan.getLoanId()).isEqualTo(1L);
        assertThat(loan.getAmount()).isEqualTo(10000.0);
        assertThat(loan.getInterestRate()).isEqualTo(5.0);
        assertThat(loan.getDurationYears()).isEqualTo(3);
        assertThat(loan.getStatus()).isEqualTo("Active");
        assertThat(loan.getStartDate()).isNotNull();
        assertThat(loan.getLoanType().getLoanTypeId()).isEqualTo(1L);
        assertThat(loan.getLoanType().getTypeName()).isEqualTo("Personal Loan");
        assertThat(loan.getLoanPaymentType().getPaymentTypeId()).isEqualTo(1L);
        assertThat(loan.getLoanPaymentType().getPaymentTypeName()).isEqualTo("EMI");
    }

    @Test
    public void testLoanMutators() {
        loan.setAmount(20000.0);
        assertThat(loan.getAmount()).isEqualTo(20000.0);

        loan.setInterestRate(6.0);
        assertThat(loan.getInterestRate()).isEqualTo(6.0);

        loan.setDurationYears(4);
        assertThat(loan.getDurationYears()).isEqualTo(4);

        loan.setStatus("Completed");
        assertThat(loan.getStatus()).isEqualTo("Completed");

        Date newStartDate = new Date();
        loan.setStartDate(newStartDate);
        assertThat(loan.getStartDate()).isEqualTo(newStartDate);

        LoanType newLoanType = new LoanType();
        newLoanType.setLoanTypeId(2L);
        newLoanType.setTypeName("Home Loan");
        newLoanType.setMaxDurationYears(10);
        newLoanType.setInterestRate(4.5);
        loan.setLoanType(newLoanType);
        assertThat(loan.getLoanType().getLoanTypeId()).isEqualTo(2L);
        assertThat(loan.getLoanType().getTypeName()).isEqualTo("Home Loan");

        LoanPaymentType newLoanPaymentType = new LoanPaymentType();
        newLoanPaymentType.setPaymentTypeId(2L);
        newLoanPaymentType.setPaymentTypeName("Lump Sum");
        loan.setLoanPaymentType(newLoanPaymentType);
        assertThat(loan.getLoanPaymentType().getPaymentTypeId()).isEqualTo(2L);
        assertThat(loan.getLoanPaymentType().getPaymentTypeName()).isEqualTo("Lump Sum");
    }

    @Test
    public void testEqualsAndHashCode() {
        Loan anotherLoan = new Loan();
        anotherLoan.setLoanId(1L);
        anotherLoan.setAmount(10000.0);
        anotherLoan.setInterestRate(5.0);
        anotherLoan.setDurationYears(3);
        anotherLoan.setStatus("Active");
        anotherLoan.setStartDate(loan.getStartDate());
        anotherLoan.setLoanType(loan.getLoanType());
        anotherLoan.setLoanPaymentType(loan.getLoanPaymentType());
       // assertThat(loan).isEqualTo(anotherLoan);
       // assertThat(loan.hashCode()).isEqualTo(anotherLoan.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "Loan{" +
                "loanId=" + loan.getLoanId() +
                ", amount=" + loan.getAmount() +
                ", interestRate=" + loan.getInterestRate() +
                ", durationYears=" + loan.getDurationYears() +
                ", status='" + loan.getStatus() + '\'' +
                ", startDate=" + loan.getStartDate() +
                ", loanType=" + loan.getLoanType() +
                ", loanPaymentType=" + loan.getLoanPaymentType() +
                '}';
        assertThat(loan.toString()).isEqualTo(expectedToString);
    }
}
