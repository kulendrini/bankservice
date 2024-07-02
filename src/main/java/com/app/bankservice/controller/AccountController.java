package com.app.bankservice.controller;

import com.app.bankservice.model.AccountResponseDTO;
import com.app.bankservice.model.TransactionResponseDTO;
import com.app.bankservice.service.AccountService;
import com.app.bankservice.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/dbservice/app")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    private TransactionService transactionService;

    @Autowired
    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


    @GetMapping("/account/{username}")
    public ResponseEntity<AccountResponseDTO> getAccounts(@PathVariable("username") String username) {
        try {
            AccountResponseDTO accountResponseDTO = accountService.getAccountDetailsByUsername(username);
            return ResponseEntity.ok(accountResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/account/{accountNumber}/transactions")
    public ResponseEntity<TransactionResponseDTO> getAccountTransactions(
            @PathVariable("accountNumber") String accountNumber,
            @RequestParam(value = "from", defaultValue = "0") int from,
            @RequestParam(value = "to", defaultValue = "10") int to) {
        try {
            TransactionResponseDTO transactions = transactionService.getTransactionDetailsByAccountNo(accountNumber, from, to);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
