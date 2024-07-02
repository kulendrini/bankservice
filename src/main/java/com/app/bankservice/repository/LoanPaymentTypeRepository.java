package com.app.bankservice.repository;
import com.app.bankservice.entity.LoanPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPaymentTypeRepository extends JpaRepository<LoanPaymentType, Long> {

}
