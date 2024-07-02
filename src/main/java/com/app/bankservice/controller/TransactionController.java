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


    /**
     * Processes a transaction based on the provided inbound transaction details.
     *
     * This endpoint takes a {@link TransactionInbound} object as input, processes the transaction,
     * and returns a {@link TransactionOutbound} object with the transaction details and status.
     * If the input is invalid or if there are any issues during the transaction processing, appropriate
     * error responses are returned.
     *
     * @param transactionInbound the inbound transaction details
     * @return a {@link ResponseEntity} containing a {@link TransactionOutbound} with the transaction details if the request is successful, or an appropriate HTTP error response:
     *         <ul>
     *             <li>200 OK: The transaction was successfully processed and details are included in the response body</li>
     *             <li>400 Bad Request: The input transaction details are invalid</li>
     *             <li>500 Internal Server Error: An unexpected server error occurred during the processing of the transaction</li>
     *         </ul>
     * @throws IllegalArgumentException if the input transaction details are invalid
     * @throws RuntimeException for unexpected server errors
     */
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


    /**
     * Retrieves the details of a specific transaction based on the provided transaction ID.
     *
     * This endpoint takes a {@code transactionId} as a path variable, retrieves the corresponding transaction details,
     * and returns a {@link TransactionDetailsDTO} object with the transaction information.
     * If the transaction ID is invalid or if there are any issues retrieving the transaction details, appropriate
     * error responses are returned.
     *
     * @param transactionId the ID of the transaction to retrieve details for
     * @return a {@link ResponseEntity} containing a {@link TransactionDetailsDTO} with the transaction details if the request is successful, or an appropriate HTTP error response:
     *         <ul>
     *             <li>200 OK: The transaction details are included in the response body</li>
     *             <li>400 Bad Request: The provided transaction ID is invalid</li>
     *             <li>500 Internal Server Error: An unexpected server error occurred while retrieving the transaction details</li>
     *         </ul>
     * @throws IllegalArgumentException if the provided transaction ID is invalid
     * @throws RuntimeException for unexpected server errors
     */
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
