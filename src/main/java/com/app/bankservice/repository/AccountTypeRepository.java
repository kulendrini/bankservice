package com.app.bankservice.repository;

import com.app.bankservice.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository  extends JpaRepository<AccountType, Long> {

}
