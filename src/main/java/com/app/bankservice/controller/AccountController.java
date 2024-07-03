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
     * Retrieves the account details for a specified username.
     *
     * This endpoint is used to fetch account details associated with the given username.
     * The username must not be empty; otherwise, a validation error will be triggered.
     *
     * @param username The username of the account holder for which details are to be fetched.
     *                 Must not be empty.
     * @return A {@link ResponseEntity} containing an {@link AccountResponseDTO} with account details
     *         and an HTTP status of {@code 200 OK} if the account details are successfully retrieved.
     *
     * @throws IllegalArgumentException if the username is empty.
     */
    @GetMapping("/account/{username}")
    public ResponseEntity<AccountResponseDTO> getAccounts(@PathVariable("username") @NotEmpty(message = "Username cannot be empty") String username) {
        AccountResponseDTO accountResponseDTO = accountService.getAccountDetailsByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(accountResponseDTO);
    }

    /**
     * Retrieves the transaction details for a specified account number within a given range.
     *
     * This endpoint fetches the transactions associated with the provided account number. The range of transactions
     * can be specified using the `from` and `to` query parameters, which default to 0 and 10, respectively, if not provided.
     * The account number must not be empty; otherwise, a validation error will be triggered.
     *
     * @param accountNumber The account number for which transactions are to be fetched.
     *                      Must not be empty.
     * @param from The starting index of the transaction records to be retrieved. Defaults to 0.
     * @param to The ending index (exclusive) of the transaction records to be retrieved. Defaults to 10.
     * @return A {@link ResponseEntity} containing a {@link TransactionResponseDTO} with transaction details
     *         and an HTTP status of {@code 200 OK} if the transaction details are successfully retrieved.
     *
     * @throws IllegalArgumentException if the account number is empty.
     */
    @GetMapping("/account/{accountNumber}/transactions")
    public ResponseEntity<TransactionResponseDTO> getAccountTransactions(
            @PathVariable("accountNumber") @NotEmpty(message = "Account number cannot be empty") String accountNumber,
            @RequestParam(value = "from", defaultValue = "0") int from,
            @RequestParam(value = "to", defaultValue = "10") int to) {
        TransactionResponseDTO transactions = transactionService.getTransactionDetailsByAccountNo(accountNumber, from, to);
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }


}
