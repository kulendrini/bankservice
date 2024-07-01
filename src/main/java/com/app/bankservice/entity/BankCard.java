package com.app.bankservice.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BankCard {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardID;

//    @ManyToOne
//    @JoinColumn(name = "userID", nullable = false)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "accountID", nullable = false)
    private Account account;

    private String cardNumber;

//    @ManyToOne
//    @JoinColumn(name = "cardTypeID", nullable = false)
    private CardType cardType;

    private Double creditLimit;

//    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    private String status;
}
