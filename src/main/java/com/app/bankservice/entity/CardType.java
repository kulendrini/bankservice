package com.app.bankservice.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "card_type")
public class CardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardTypeID;
    private String typeName;

    @OneToMany(targetEntity = BankCard.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "card_type_id", referencedColumnName = "cardTypeID")
    private List<BankCard> bankCards;

    public CardType() {
    }

    public CardType(Long cardTypeID, String typeName, List<BankCard> bankCards) {
        this.cardTypeID = cardTypeID;
        this.typeName = typeName;
        this.bankCards = bankCards;
    }

    public Long getCardTypeID() {
        return cardTypeID;
    }

    public void setCardTypeID(Long cardTypeID) {
        this.cardTypeID = cardTypeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<BankCard> getBankCards() {
        return bankCards;
    }

    public void setBankCards(List<BankCard> bankCards) {
        this.bankCards = bankCards;
    }

    @Override
    public String toString() {
        return "CardType{" +
                "cardTypeID=" + cardTypeID +
                ", typeName='" + typeName + '\'' +
                ", bankCards=" + bankCards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardType cardType = (CardType) o;
        return Objects.equals(cardTypeID, cardType.cardTypeID) && Objects.equals(typeName, cardType.typeName) && Objects.equals(bankCards, cardType.bankCards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardTypeID, typeName, bankCards);
    }
}
