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
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        User user;
        try {
            user = userRepository.findByUsername(username);
            if (user == null) {
                throw new IllegalArgumentException("User not found for username: " + username);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user details for username: " + username, e);
        }

        List<Loan> loans;
        try {
            loans = user.getLoans();
            if (loans == null || loans.isEmpty()) {
                throw new IllegalArgumentException("No loans found for user: " + username);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching loans for user: " + username, e);
        }

        List<LoanDTO> loanDTOS = new ArrayList<>();
        for (Loan loan : loans) {
            LoanDTO loanDTO = new LoanDTO();
            try {
                loanDTO.setAmount(loan.getAmount());
                loanDTO.setInterestRate(loan.getInterestRate());
                loanDTO.setDurationYears(loan.getDurationYears());
                loanDTO.setStatus(loan.getStatus());
                loanDTO.setStartDate(loan.getStartDate());
                loanDTO.setLoanType(loan.getLoanType().getTypeName());
                loanDTO.setLoanPaymentType(loan.getLoanPaymentType().getPaymentTypeName());
                loanDTOS.add(loanDTO);
            } catch (Exception e) {
                throw new RuntimeException("Error processing loan details for loan ID: " + loan.getLoanId(), e);
            }
        }

        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        loanResponseDTO.setLoans(loanDTOS);
        loanResponseDTO.setFirstName(user.getFirstName());
        loanResponseDTO.setLastName(user.getLastName());

        return loanResponseDTO;
    }

}
