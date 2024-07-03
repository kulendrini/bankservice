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
     * Retrieves loan details for a specific user identified by their username.
     *
     * This endpoint fetches the loan details for the user specified by the {@code username} path variable. It returns a {@link LoanResponseDTO} containing information about the user's loans, including loan amounts, interest rates, durations, and types.
     * If the username is invalid or if there are any issues during the retrieval process, appropriate error responses are returned.
     *
     * @param username the username of the user whose loan details are to be retrieved
     * @return a {@link ResponseEntity} containing a {@link LoanResponseDTO} with the user's loan details if the request is successful, or an appropriate HTTP error response:
     *         <ul>
     *             <li>200 OK: The loan details were successfully retrieved and are included in the response body</li>
     *             <li>400 Bad Request: The username provided is invalid or not found</li>
     *             <li>500 Internal Server Error: An unexpected server error occurred during the processing of the request</li>
     *         </ul>
     * @throws IllegalArgumentException if the username is invalid or if the loan details cannot be found
     * @throws RuntimeException for unexpected server errors
     * @throws Exception for any other unforeseen exceptions
     */
    @GetMapping("/loan/{username}")
    public ResponseEntity<?> getLoanDetails(@PathVariable("username") @NotEmpty(message = "Username cannot be empty") String username) {

        try {
            LoanResponseDTO loanResponseDTO = loanService.getLoanDetailsByUserName(username);
            return ResponseEntity.status(HttpStatus.OK).body(loanResponseDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("Client error: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected server error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
