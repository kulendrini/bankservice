package com.app.bankservice.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class BankCardTest {

    private BankCard bankCard;

    @BeforeEach
    public void setUp() {
        CardType cardType = new CardType();
        cardType.setCardTypeID(1L);
        cardType.setTypeName("Credit");

        bankCard = new BankCard();
        bankCard.setCardID(1L);
        bankCard.setCardNumber("1234567890123456");
        bankCard.setCreditLimit(5000.0);
        bankCard.setStatus("Active");
        bankCard.setExpiryDate(new Date(2025, 12, 31));  // Deprecated constructor used for example
        bankCard.setCardType(cardType);
    }

    @Test
    public void testBankCardFields() {
        assertThat(bankCard.getCardID()).isEqualTo(1L);
        assertThat(bankCard.getCardNumber()).isEqualTo("1234567890123456");
        assertThat(bankCard.getCreditLimit()).isEqualTo(5000.0);
        assertThat(bankCard.getStatus()).isEqualTo("Active");
        assertThat(bankCard.getExpiryDate()).isNotNull();
        assertThat(bankCard.getCardType()).isNotNull();
        assertThat(bankCard.getCardType().getTypeName()).isEqualTo("Credit");
    }

    @Test
    public void testBankCardMutators() {
        CardType newCardType = new CardType();
        newCardType.setCardTypeID(2L);
        newCardType.setTypeName("Debit");
        bankCard.setCardType(newCardType);
        assertThat(bankCard.getCardType().getTypeName()).isEqualTo("Debit");

        bankCard.setCardNumber("9876543210987654");
        assertThat(bankCard.getCardNumber()).isEqualTo("9876543210987654");

        bankCard.setCreditLimit(10000.0);
        assertThat(bankCard.getCreditLimit()).isEqualTo(10000.0);

        bankCard.setStatus("Inactive");
        assertThat(bankCard.getStatus()).isEqualTo("Inactive");

        bankCard.setExpiryDate(new Date(2026, 11, 30));  // Deprecated constructor used for example
        assertThat(bankCard.getExpiryDate()).isNotNull();
    }

    @Test
    public void testEqualsAndHashCode() {
        CardType cardType = new CardType();
        cardType.setCardTypeID(1L);
        cardType.setTypeName("Credit");

        BankCard anotherBankCard = new BankCard();
        anotherBankCard.setCardID(1L);
        anotherBankCard.setCardNumber("1234567890123456");
        anotherBankCard.setCreditLimit(5000.0);
        anotherBankCard.setStatus("Active");
        anotherBankCard.setExpiryDate(new Date(2025, 12, 31));  // Deprecated constructor used for example
        anotherBankCard.setCardType(cardType);

        assertThat(bankCard).isEqualTo(anotherBankCard);
        assertThat(bankCard.hashCode()).isEqualTo(anotherBankCard.hashCode());
    }

    @Test
    public void testDefaultConstructor() {
        BankCard defaultBankCard = new BankCard();
        assertThat(defaultBankCard.getCardID()).isNull();
        assertThat(defaultBankCard.getCardNumber()).isNull();
        assertThat(defaultBankCard.getCreditLimit()).isNull();
        assertThat(defaultBankCard.getStatus()).isNull();
        assertThat(defaultBankCard.getExpiryDate()).isNull();
        assertThat(defaultBankCard.getCardType()).isNull();
    }
}