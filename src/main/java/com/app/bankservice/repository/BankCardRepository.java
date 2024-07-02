package com.app.bankservice.repository;

import com.app.bankservice.entity.Account;
import com.app.bankservice.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRepository  extends JpaRepository<BankCard, Long> {

}
