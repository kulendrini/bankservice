package com.app.bankservice.service;

import com.app.bankservice.entity.Loan;
import com.app.bankservice.entity.User;
import com.app.bankservice.model.LoanDTO;
import com.app.bankservice.model.LoanResponseDTO;
import com.app.bankservice.repository.UserRepository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    private final UserRepository userRepository;

    @Autowired
    public LoanService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Retrieves loan details for a given username.
     *
     * This method fetches the {@link User} by the specified username and retrieves all associated loans. It processes the loans to create a list of {@link LoanDTO} objects. If the username is null, empty, or if no user or loans are found, appropriate exceptions are thrown.
     *
     *
     * @param username The username of the user whose loan details are to be retrieved. This cannot be null or empty.
     * @return A {@link LoanResponseDTO} object containing the user's first name, last name, and a list of {@link LoanDTO} objects with loan details.
     * @throws IllegalArgumentException if the username is null, empty, if no user is found for the given username, or if no loans are associated with the user.
     * @throws RuntimeException if there is an error retrieving user details or processing loan information.
     * @see User
     * @see Loan
     * @see LoanDTO
     * @see LoanResponseDTO
     */
    public LoanResponseDTO getLoanDetailsByUserName(@NotEmpty(message = "Username cannot be empty") String username) {
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
