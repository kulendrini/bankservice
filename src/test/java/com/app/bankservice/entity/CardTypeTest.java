package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardTypeTest {

    private CardType cardType;

    @BeforeEach
    public void setUp() {
        // Creating a BankCard instance for testing
        BankCard bankCard = new BankCard();
        bankCard.setCardID(1L);
        bankCard.setCardNumber("1234567890123456");
        bankCard.setCreditLimit(5000.0);
        bankCard.setStatus("Active");
        bankCard.setExpiryDate(new Date(2025, 12, 31));  // Deprecated constructor used for example

        // Setting up CardType instance
        cardType = new CardType();
        cardType.setCardTypeID(1L);
        cardType.setTypeName("Credit");

        List<BankCard> bankCards = new ArrayList<>();
        bankCards.add(bankCard);
        cardType.setBankCards(bankCards);
    }

    @Test
    public void testCardTypeFields() {
        assertThat(cardType.getCardTypeID()).isEqualTo(1L);
        assertThat(cardType.getTypeName()).isEqualTo("Credit");

        // We are adding the same BankCard in setUp method.
        assertThat(cardType.getBankCards()).hasSize(1);
        assertThat(cardType.getBankCards().get(0).getCardNumber()).isEqualTo("1234567890123456");
    }

    @Test
    public void testCardTypeMutators() {
        cardType.setTypeName("Debit");
        assertThat(cardType.getTypeName()).isEqualTo("Debit");

        BankCard newBankCard = new BankCard();
        newBankCard.setCardID(2L);
        newBankCard.setCardNumber("9876543210987654");
        newBankCard.setCreditLimit(10000.0);
        newBankCard.setStatus("Inactive");
        newBankCard.setExpiryDate(new Date(2026, 11, 30));  // Deprecated constructor used for example

        List<BankCard> bankCards = new ArrayList<>();
        bankCards.add(newBankCard);
        cardType.setBankCards(bankCards);
        assertThat(cardType.getBankCards()).hasSize(1);
        assertThat(cardType.getBankCards().get(0).getCardNumber()).isEqualTo("9876543210987654");
    }

    @Test
    public void testEqualsAndHashCode() {
        CardType anotherCardType = new CardType();
        anotherCardType.setCardTypeID(1L);
        anotherCardType.setTypeName("Credit");
        anotherCardType.setBankCards(cardType.getBankCards());

        assertThat(cardType).isEqualTo(anotherCardType);
        assertThat(cardType.hashCode()).isEqualTo(anotherCardType.hashCode());
    }

    @Test
    public void testDefaultConstructor() {
        CardType defaultCardType = new CardType();
        assertThat(defaultCardType.getCardTypeID()).isNull();
        assertThat(defaultCardType.getTypeName()).isNull();
        assertThat(defaultCardType.getBankCards()).isNull();
    }
}