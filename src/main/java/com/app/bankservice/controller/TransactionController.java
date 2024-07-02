package com.app.bankservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.app.bankservice.model.*;
import com.app.bankservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/dbservice/app")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/transaction")
    public ResponseEntity<TransactionOutbound> makeTransaction(@RequestBody TransactionInbound transactionInbound) {
        try {
            TransactionOutbound transactionOutbound = transactionService.makeTransaction(transactionInbound);
            return ResponseEntity.ok(transactionOutbound);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionDetailsDTO> getTransactionById(@PathVariable("transactionId") Long transactionId) {
        try {
            TransactionDetailsDTO transactionDetails = transactionService.getTransactionById(transactionId);
            return ResponseEntity.ok(transactionDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            logger.error("Unexpected error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
