package com.app.bankservice.repository;

import com.app.bankservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.account.accountId = :accountId AND t.transactionId BETWEEN :from AND :to ORDER BY t.transactionId DESC")
    List<Transaction> findByAccountIdAndIdRange(@Param("accountId") Long accountId, @Param("from") int from, @Param("to") int to);

    Transaction findByTransactionId(Long transactionId);
}
