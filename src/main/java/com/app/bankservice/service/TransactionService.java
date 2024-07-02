package com.app.bankservice.service;

import com.app.bankservice.entity.Account;
import com.app.bankservice.entity.Transaction;
import com.app.bankservice.entity.TransactionType;
import com.app.bankservice.model.*;
import com.app.bankservice.repository.AccountRepository;
import com.app.bankservice.repository.TransactionRepository;
import com.app.bankservice.repository.TransactionTypeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionTypeRepository transactionTypeRepository;


    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionTypeRepository transactionTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }



    public TransactionResponseDTO getTransactionDetailsByAccountNo (String accountNumber, int from, int to) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }

        Account account;
        try {
            account = accountRepository.findByAccountNumber(accountNumber);
            if (account == null) {
                throw new IllegalArgumentException("Account not found for account number: " + accountNumber);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching account", e);
        }

        List<Transaction> transactionList;
        try {
            transactionList = transactionRepository.findByAccountIdAndIdRange(account.getAccountId(), from, to);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching transactions", e);
        }

        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        try {
            transactionResponseDTO.setAccountNumber(account.getAccountNumber());
            transactionResponseDTO.setBalance(account.getBalance());
            transactionResponseDTO.setStatus(account.getStatus());
            transactionResponseDTO.setCreatedDate(account.getCreatedDate());
            transactionResponseDTO.setAccountType(account.getAccountType().getAccountTypeName());
            transactionResponseDTO.setCurrencyType(account.getCurrencyType().getCurrencyName());

            List<TransactionDTO> transactionDTOS = new ArrayList<>();
            for (Transaction transaction : transactionList) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setDescription(transaction.getDescription());
                transactionDTO.setAmount(transaction.getAmount());
                transactionDTO.setTransactionDate(transaction.getTransactionDate());
                transactionDTO.setTransactionType(transaction.getTransactionType().getTypeName());
                transactionDTOS.add(transactionDTO);
            }
            transactionResponseDTO.setTransactionDTOS(transactionDTOS);
        } catch (Exception e) {
            throw new RuntimeException("Error setting transaction response details", e);
        }

        return transactionResponseDTO;
    }



    public TransactionDetailsDTO getTransactionById(Long transactionId){
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction ID cannot be null");
        }

        Transaction transaction;
        try {
            transaction = transactionRepository.findByTransactionId(transactionId);
            if (transaction == null) {
                throw new IllegalArgumentException("Transaction not found for ID: " + transactionId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching transaction", e);
        }

        TransactionDetailsDTO transactionDetailsDTO = new TransactionDetailsDTO();
        try {
            transactionDetailsDTO.setTransactionId(transaction.getTransactionId());
            transactionDetailsDTO.setAccountNumber(transaction.getAccount().getAccountNumber());
            transactionDetailsDTO.setDescription(transaction.getDescription());
            transactionDetailsDTO.setAmount(transaction.getAmount());
            transactionDetailsDTO.setTransactionDate(transaction.getTransactionDate());
            transactionDetailsDTO.setTransactionType(transaction.getTransactionType().getTypeName());
        } catch (Exception e) {
            throw new RuntimeException("Error setting transaction details", e);
        }

        return transactionDetailsDTO;
    }



    @Transactional
    public TransactionOutbound makeTransaction(TransactionInbound transactionInbound) {
        if (transactionInbound == null) {
            throw new IllegalArgumentException("Transaction inbound data cannot be null");
        }
        try {
            return processTransaction(transactionInbound);
        } catch (IllegalArgumentException e) {
            logger.warn("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            throw new RuntimeException("Error processing transaction", e);
        }
    }

    private TransactionOutbound processTransaction(TransactionInbound transactionInbound) {
        Transaction savedTransaction;
        try {
            savedTransaction = saveTransaction(transactionInbound);
        } catch (Exception e) {
            throw new RuntimeException("Error saving transaction", e);
        }

        Account newAccount;
        try {
            newAccount = accountRepository.findByAccountNumber(transactionInbound.getOriginAccountNo());
            if (newAccount == null) {
                throw new IllegalArgumentException("Account not found for account number: " + transactionInbound.getOriginAccountNo());
            }
            entityManager.refresh(newAccount);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching account", e);
        }

        TransactionOutbound transactionOutbound = new TransactionOutbound();
        try {
            transactionOutbound.setSenderAccountNo(newAccount.getAccountNumber());
            transactionOutbound.setBeneficiaryAccountNo(savedTransaction.getDestinationAccountNo());
            transactionOutbound.setBeneficiaryBank(savedTransaction.getBank());
            transactionOutbound.setTransferCurrency(newAccount.getCurrencyType().getCurrencyName());
            transactionOutbound.setTransferAmount(savedTransaction.getAmount());
            transactionOutbound.setSenderAccountBalance(newAccount.getBalance());
            transactionOutbound.setTransferStatus(TransferStatus.SUCCESS);

        } catch (Exception e) {
            throw new RuntimeException("Error setting transaction outbound details", e);
        }

        return transactionOutbound;
    }

    private Transaction saveTransaction(TransactionInbound transactionInbound) {
        Account account;
        try {
            account = accountRepository.findByAccountNumber(transactionInbound.getOriginAccountNo());
            if (account == null) {
                throw new IllegalArgumentException("Account not found for account number: " + transactionInbound.getOriginAccountNo());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching account", e);
        }

        TransactionType transactionType;
        try {
            transactionType = transactionTypeRepository.findByTransactionTypeId(transactionInbound.getTransactionTypeId());
            if (transactionType == null) {
                throw new IllegalArgumentException("Transaction type not found for ID: " + transactionInbound.getTransactionTypeId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching transaction type", e);
        }

        double newBalance = account.getBalance() - transactionInbound.getAmount();
        if (newBalance < 0) {
            throw new IllegalArgumentException("Insufficient funds for transaction");
        }

        try {
            int status = accountRepository.updateBalance(newBalance, account.getAccountNumber());
            if (status == 0) {
                throw new RuntimeException("Failed to update account balance");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating account balance", e);
        }

        Transaction transaction = new Transaction();
        try {
            transaction.setAmount(transactionInbound.getAmount());
            transaction.setDescription(transactionInbound.getComment());
            transaction.setDestinationAccountNo(transactionInbound.getDestinationAccountNo());
            transaction.setBank(transactionInbound.getBank());
            transaction.setTransactionDate(transactionInbound.getTransactionDate());
            transaction.setAccount(account);
            transaction.setTransactionType(transactionType);
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new RuntimeException("Error saving transaction", e);
        }
    }

}
