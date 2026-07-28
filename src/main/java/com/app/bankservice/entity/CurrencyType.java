package com.app.bankservice.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "currency_type")
public class CurrencyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currencyTypeId;
    private String currencyName;

    @OneToMany(targetEntity = Account.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_type_id", referencedColumnName = "currencyTypeId")
    private List<Account> accounts;

    public CurrencyType() {
    }

    public CurrencyType(Long currencyTypeId, String currencyName, List<Account> accounts) {
        this.currencyTypeId = currencyTypeId;
        this.currencyName = currencyName;
        this.accounts = accounts;
    }

    public Long getCurrencyTypeId() {
        return currencyTypeId;
    }

    public void setCurrencyTypeId(Long currencyTypeId) {
        this.currencyTypeId = currencyTypeId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "CurrencyType{" +
                "currencyTypeId=" + currencyTypeId +
                ", currencyName='" + currencyName + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyType that = (CurrencyType) o;
        return Objects.equals(currencyTypeId, that.currencyTypeId) && Objects.equals(currencyName, that.currencyName) && Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyTypeId, currencyName, accounts);
    }
}
