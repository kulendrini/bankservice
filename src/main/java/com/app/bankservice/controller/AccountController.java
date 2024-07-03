package com.app.bankservice.controller;

import com.app.bankservice.model.AccountResponseDTO;
import com.app.bankservice.model.TransactionResponseDTO;
import com.app.bankservice.service.AccountService;
import com.app.bankservice.service.TransactionService;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/dbservice/app")
@Validated
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private TransactionService transactionService;

    @Autowired
    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


    /**
     * Retrieves account details for a specified username.
     *
     * This endpoint fetches account details based on the provided username. It uses the `AccountService` to retrieve the account information and returns it in the `AccountResponseDTO` format.
     * If the username does not exist or if there is a problem with the service layer, appropriate HTTP status codes are returned.
     *
     * @param username the username of the account holder for which the details are to be fetched
     *                 (must be a valid, non-null string)
     * @return a {@link ResponseEntity} containing the {@link AccountResponseDTO} with account details if successful;
     *         or an appropriate HTTP status code if an error occurs:
     *         <ul>
     *             <li>400 Bad Request if the username is invalid or not found</li>
     *             <li>500 Internal Server Error if there is an unexpected error during processing</li>
     *         </ul>
     * @throws IllegalArgumentException if the username is invalid or not found in the database
     * @throws RuntimeException if an unexpected error occurs while retrieving account details
     * @see AccountService#getAccountDetailsByUsername(String)
     * @see AccountResponseDTO
     */
    @GetMapping("/account/{username}")
    public ResponseEntity<AccountResponseDTO> getAccounts(@PathVariable("username") @NotEmpty(message = "Username cannot be empty") String username) {
        try {
            AccountResponseDTO accountResponseDTO = accountService.getAccountDetailsByUsername(username);
            return ResponseEntity.status(HttpStatus.OK).body(accountResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retrieves transaction details for a specified account number.
     *
     * This endpoint fetches a list of transactions for the given account number within the specified range of transaction IDs.
     * It uses the `TransactionService` to retrieve the transaction details and returns them in the `TransactionResponseDTO` format.
     * If the account number is invalid, the `from` or `to` parameters are incorrect, or if there is an error during processing, appropriate HTTP status codes are returned.
     *
     * @param accountNumber the account number for which transactions are to be fetched
     *                      (must be a valid, non-null string representing the account number)
     * @param from  the starting transaction ID for the range (defaults to 0 if not provided)
     * @param to    the ending transaction ID for the range (defaults to 10 if not provided)
     * @return a {@link ResponseEntity} containing the {@link TransactionResponseDTO} with transaction details if successful;
     *         or an appropriate HTTP status code if an error occurs:
     *         <ul>
     *             <li>400 Bad Request if the account number is invalid or if the `from` or `to` parameters are incorrect</li>
     *             <li>500 Internal Server Error if there is an unexpected error during processing</li>
     *         </ul>
     * @throws IllegalArgumentException if the account number is invalid or if the `from` or `to` parameters are out of bounds
     * @throws RuntimeException if an unexpected error occurs while retrieving transaction details
     * @see TransactionService#getTransactionDetailsByAccountNo(String, int, int)
     * @see TransactionResponseDTO
     */
    @GetMapping("/account/{accountNumber}/transactions")
    public ResponseEntity<TransactionResponseDTO> getAccountTransactions(
            @PathVariable("accountNumber") @NotEmpty(message = "Account number cannot be empty") String accountNumber,
            @RequestParam(value = "from", defaultValue = "0") int from,
            @RequestParam(value = "to", defaultValue = "10") int to) {
        try {
            TransactionResponseDTO transactions = transactionService.getTransactionDetailsByAccountNo(accountNumber, from, to);
            return ResponseEntity.status(HttpStatus.OK).body(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
