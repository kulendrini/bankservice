package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LoanTypeTest {

    private LoanType loanType;

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

        loanType = new LoanType();
        loanType.setLoanTypeId(1L);
        loanType.setTypeName("Personal Loan");
        loanType.setMaxDurationYears(5);
        loanType.setInterestRate(5.0);
        loanType.setLoans(loans);
    }

    @Test
    public void testLoanTypeFields() {
        assertThat(loanType.getLoanTypeId()).isEqualTo(1L);
        assertThat(loanType.getTypeName()).isEqualTo("Personal Loan");
        assertThat(loanType.getMaxDurationYears()).isEqualTo(5);
        assertThat(loanType.getInterestRate()).isEqualTo(5.0);
        assertThat(loanType.getLoans()).hasSize(1);
        assertThat(loanType.getLoans().get(0).getLoanId()).isEqualTo(1L);
    }

    @Test
    public void testLoanTypeMutators() {
        loanType.setTypeName("Home Loan");
        assertThat(loanType.getTypeName()).isEqualTo("Home Loan");

        loanType.setMaxDurationYears(10);
        assertThat(loanType.getMaxDurationYears()).isEqualTo(10);

        loanType.setInterestRate(4.5);
        assertThat(loanType.getInterestRate()).isEqualTo(4.5);

        Loan newLoan = new Loan();
        newLoan.setLoanId(2L);
        newLoan.setAmount(20000.0);
        newLoan.setInterestRate(4.5);
        newLoan.setDurationYears(5);
        newLoan.setStatus("Approved");

        List<Loan> loans = new ArrayList<>();
        loans.add(newLoan);
        loanType.setLoans(loans);
        assertThat(loanType.getLoans()).hasSize(1);
        assertThat(loanType.getLoans().get(0).getLoanId()).isEqualTo(2L);
    }

    @Test
    public void testEqualsAndHashCode() {
        LoanType anotherLoanType = new LoanType();
        anotherLoanType.setLoanTypeId(1L);
        anotherLoanType.setTypeName("Personal Loan");
        anotherLoanType.setMaxDurationYears(5);
        anotherLoanType.setInterestRate(5.0);
        anotherLoanType.setLoans(loanType.getLoans());

        assertThat(loanType).isEqualTo(anotherLoanType);
        assertThat(loanType.hashCode()).isEqualTo(anotherLoanType.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "LoanType{" +
                "loanTypeId=" + loanType.getLoanTypeId() +
                ", typeName='" + loanType.getTypeName() + '\'' +
                ", maxDurationYears=" + loanType.getMaxDurationYears() +
                ", interestRate=" + loanType.getInterestRate() +
                ", loans=" + loanType.getLoans() +
                '}';
        assertThat(loanType.toString()).isEqualTo(expectedToString);
    }
}