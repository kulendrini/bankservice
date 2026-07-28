package com.app.bankservice.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "bank_card")
public class BankCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardID;

    private String cardNumber;
    private Double creditLimit;
    private String status;
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name = "card_type_id")
    private CardType cardType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public BankCard() {
    }

    public BankCard(Long cardID, String cardNumber, Double creditLimit, String status, Date expiryDate, CardType cardType) {
        this.cardID = cardID;
        this.cardNumber = cardNumber;
        this.creditLimit = creditLimit;
        this.status = status;
        this.expiryDate = expiryDate;
        this.cardType = cardType;
    }

    public Long getCardID() {
        return cardID;
    }

    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "cardID=" + cardID +
                ", cardNumber='" + cardNumber + '\'' +
                ", creditLimit=" + creditLimit +
                ", status='" + status + '\'' +
                ", expiryDate=" + expiryDate +
                ", cardType=" + cardType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return Objects.equals(cardID, bankCard.cardID) && Objects.equals(cardNumber, bankCard.cardNumber) && Objects.equals(creditLimit, bankCard.creditLimit) && Objects.equals(status, bankCard.status) && Objects.equals(expiryDate, bankCard.expiryDate) && Objects.equals(cardType, bankCard.cardType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardID, cardNumber, creditLimit, status, expiryDate, cardType);
    }
}
