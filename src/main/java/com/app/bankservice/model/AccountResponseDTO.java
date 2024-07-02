package com.app.bankservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AccountResponseDTO {

    private String firstName;
    private String lastName;
    @JsonProperty("accounts")
    private List<AccountDTO> accountDTOList;

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

    public List<AccountDTO> getAccountDTOList() {
        return accountDTOList;
    }

    public void setAccountDTOList(List<AccountDTO> accountDTOList) {
        this.accountDTOList = accountDTOList;
    }
}
