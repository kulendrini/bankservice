package com.app.bankservice.entity;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

@Entity
@Table (name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

    private Long accountId;
    private String accountNumber;
    private Double balance;
    private String status;
    private Double minBalance;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private Double maxTransactionAmount;
//    @ManyToOne
//    @JoinColumn(name = "userId", nullable = false)
    private User user;



//    @ManyToOne
//    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;


//    @ManyToOne
//    @JoinColumn(name = "currency_type_id", nullable = false)
    private CurrencyType currencyType;

//
}
