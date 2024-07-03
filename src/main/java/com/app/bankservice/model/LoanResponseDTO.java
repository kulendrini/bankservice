package com.app.bankservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LoanResponseDTO {

    private String firstName;
    private String lastName;
    @JsonProperty("loans")
    private List<LoanDTO> loans;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<LoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanDTO> loans) {
        this.loans = loans;
    }
}
