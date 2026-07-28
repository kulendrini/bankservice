package com.app.bankservice.controller;

import com.app.bankservice.model.TransactionDetailsDTO;
import com.app.bankservice.model.TransactionInbound;
import com.app.bankservice.model.TransactionOutbound;
import com.app.bankservice.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/dbservice/app")
@Validated
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    /**
     * Processes a transaction based on the provided transaction details.
     *
     * This endpoint processes a transaction request by accepting a {@link TransactionInbound} object containing the
     * transaction details. Upon successful processing, it returns a {@link TransactionOutbound} object with the details
     * of the completed transaction.
     *
     * @param transactionInbound The details of the transaction to be processed. Must be a valid {@link TransactionInbound}
     *                           object with non-null fields.
     * @return A {@link ResponseEntity} containing a {@link TransactionOutbound} with the details of the completed transaction
     *         and an HTTP status of {@code 200 OK} if the transaction is successfully processed.
     *
     * @throws MethodArgumentNotValidException if the {@link TransactionInbound} object contains invalid data.
     */
    @PostMapping("/transaction")
    public ResponseEntity<TransactionOutbound> makeTransaction(@Valid @RequestBody TransactionInbound transactionInbound) {
        TransactionOutbound transactionOutbound = transactionService.makeTransaction(transactionInbound);
        return ResponseEntity.status(HttpStatus.OK).body(transactionOutbound);
    }


    /**
     * Retrieves the details of a specific transaction based on the provided transaction ID.
     *
     * This endpoint fetches the transaction details for the given transaction ID. The transaction ID must not be null;
     * otherwise, a validation error will be triggered.
     *
     * @param transactionId The ID of the transaction whose details are to be retrieved. Must not be null.
     * @return A {@link ResponseEntity} containing a {@link TransactionDetailsDTO} with the details of the specified transaction
     *         and an HTTP status of {@code 200 OK} if the transaction details are successfully retrieved.
     *
     * @throws IllegalArgumentException if the transaction ID is null.
     */
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionDetailsDTO> getTransactionById(@PathVariable("transactionId") @NotNull Long transactionId) {
        TransactionDetailsDTO transactionDetails = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transactionDetails);
    }

}
