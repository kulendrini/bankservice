package com.app.bankservice.entity;

import java.util.Date;

public class Transaction {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

//    @ManyToOne
//    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

//    @ManyToOne
//    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;

//    @Column(name = "amount", nullable = false)
    private Double amount;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

//    @Column(name = "description")
    private String description;
}
