package com.app.bankservice.entity;

import lombok.Data;

import java.util.Set;

@Data
public class CardType {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardTypeID;

    private String typeName;

//    @OneToMany(mappedBy = "cardType")
    private Set<BankCard> bankCards;
}
