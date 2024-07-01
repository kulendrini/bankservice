package com.app.bankservice.entity;

import lombok.Data;

@Data
public class TransactionType {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionTypeId;
    private String typeName;
}
