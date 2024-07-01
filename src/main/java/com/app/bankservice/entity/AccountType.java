package com.app.bankservice.entity;

import lombok.*;

@Data
public class AccountType {

    private Long accountTypeId;
    private String accountTypeName;
    private double interestRate;

}
