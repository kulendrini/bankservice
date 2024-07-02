package com.app.bankservice.repository;

import com.app.bankservice.entity.Account;
import com.app.bankservice.entity.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyTypeRepository  extends JpaRepository<CurrencyType, Long> {

}
