package com.app.bankservice.controller;

import com.app.bankservice.model.LoanResponseDTO;
import com.app.bankservice.service.LoanService;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/dbservice/app")
@Validated
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Retrieves loan details for a specified username.
     *
     * This endpoint fetches the loan details associated with the provided username. The username must not be empty;
     * otherwise, a validation error will be triggered.
     *
     * @param username The username of the user for whom loan details are to be fetched. Must not be empty.
     * @return A {@link ResponseEntity} containing a {@link LoanResponseDTO} with the loan details and an HTTP status
     *         of {@code 200 OK} if the loan details are successfully retrieved.
     *
     * @throws IllegalArgumentException if the username is empty.
     */
    @GetMapping("/loan/{username}")
    public ResponseEntity<?> getLoanDetails(@PathVariable("username") @NotEmpty(message = "Username cannot be empty") String username) {
        LoanResponseDTO loanResponseDTO = loanService.getLoanDetailsByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(loanResponseDTO);
    }

}
