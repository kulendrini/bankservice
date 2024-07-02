package com.app.bankservice.repository;

import com.app.bankservice.entity.Account;
import com.app.bankservice.entity.LoanPaymentType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.balance = :balance WHERE a.accountNumber = :accountNumber")
    int updateBalance(@Param("balance") double balance, @Param("accountNumber") String accountNumber);

}
