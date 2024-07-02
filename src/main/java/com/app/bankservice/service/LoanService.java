package com.app.bankservice.service;

import com.app.bankservice.entity.Loan;
import com.app.bankservice.entity.User;
import com.app.bankservice.model.LoanDTO;
import com.app.bankservice.model.LoanResponseDTO;
import com.app.bankservice.repository.LoanPaymentTypeRepository;
import com.app.bankservice.repository.LoanRepository;
import com.app.bankservice.repository.LoanTypeRepository;
import com.app.bankservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoanService {

    private final UserRepository userRepository;

    @Autowired
    public LoanService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoanResponseDTO getLoanDetailsByUserName (String username){
        User user = userRepository.findByUsername(username);
        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        List<Loan> loans = user.getLoans();
        List<LoanDTO> loanDTOS = new ArrayList<>();
        for (Loan loan : loans){
            LoanDTO loanDTO = new LoanDTO();
            loanDTO.setAmount(loan.getAmount());
            loanDTO.setInterestRate(loan.getInterestRate());
            loanDTO.setDurationYears(loan.getDurationYears());
            loanDTO.setStatus(loan.getStatus());
            loanDTO.setStartDate(loan.getStartDate());
            loanDTO.setLoanType(loan.getLoanType().getTypeName());
            loanDTO.setLoanPaymentType(loan.getLoanPaymentType().getPaymentTypeName());
            loanDTOS.add(loanDTO);
        }
        loanResponseDTO.setLoans(loanDTOS);
        loanResponseDTO.setFirstName(user.getFirstName());
        loanResponseDTO.setLastName(user.getLastName());
        return loanResponseDTO;
    }

}
