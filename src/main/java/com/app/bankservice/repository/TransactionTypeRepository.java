package com.app.bankservice.repository;

import com.app.bankservice.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    TransactionType findByTransactionTypeId(Long transactionTypeId);
}
