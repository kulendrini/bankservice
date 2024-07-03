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
import jakarta.validation.constraints.NotEmpty;
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


    /**
     * Retrieves transaction details for a given account number within a specified range.
     * <p>
     * This method fetches the {@link Account} by the specified account number and retrieves all associated transactions within the provided range. It processes the transactions to create a list of {@link TransactionDTO} objects. It also includes account details such as balance, status, and type in the response. If any errors occur during the retrieval or processing of data, appropriate exceptions are thrown.
     * </p>
     *
     * @param accountNumber The account number for which transaction details are to be retrieved. This cannot be null or empty.
     * @param from The starting transaction ID for the range (inclusive).
     * @param to The ending transaction ID for the range (inclusive).
     * @return A {@link TransactionResponseDTO} object containing the account details and a list of {@link TransactionDTO} objects with transaction details.
     * @throws IllegalArgumentException if the account number is null or empty, or if no account is found for the given account number.
     * @throws RuntimeException if there is an error fetching the account or transactions, or setting transaction response details.
     * @see Account
     * @see Transaction
     * @see TransactionDTO
     * @see TransactionResponseDTO
     */
    public TransactionResponseDTO getTransactionDetailsByAccountNo (@NotEmpty(message = "Account number cannot be empty") String accountNumber, int from, int to) {
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


    /**
     * Retrieves the details of a specific transaction by its ID.
     * <p>
     * This method fetches the {@link Transaction} from the repository using the provided transaction ID. It then maps the transaction details to a {@link TransactionDetailsDTO} object which includes the transaction's ID, associated account number, description, amount, date, and type. If the transaction ID is null or no transaction is found, appropriate exceptions are thrown.
     * </p>
     *
     * @param transactionId The ID of the transaction to retrieve. This cannot be null.
     * @return A {@link TransactionDetailsDTO} object containing details of the transaction including transaction ID, account number, description, amount, date, and type.
     * @throws IllegalArgumentException if the transaction ID is null or if no transaction is found for the given ID.
     * @throws RuntimeException if there is an error fetching the transaction or setting transaction details.
     * @see Transaction
     * @see TransactionDetailsDTO
     */
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


    /**
     * Processes a new transaction based on the provided {@link TransactionInbound} data.
     * <p>
     * This method performs the necessary validation on the provided transaction data and then processes the transaction. The transaction is encapsulated in a {@link TransactionOutbound} object, which includes details such as the transaction ID, status, and any related messages. If the provided data is invalid or if an unexpected error occurs during the processing, appropriate exceptions are thrown and logged.
     * </p>
     *
     * @param transactionInbound The data required to create a new transaction. This cannot be null.
     * @return A {@link TransactionOutbound} object containing the results of the transaction process, including details such as transaction ID and status.
     * @throws IllegalArgumentException if the {@code transactionInbound} is null or invalid.
     * @throws RuntimeException if an unexpected error occurs during the transaction processing.
     * @see TransactionInbound
     * @see TransactionOutbound
     */
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


    /**
     * Processes a transaction based on the provided {@link TransactionInbound} data.
     * <p>
     * This method handles the transaction processing by first saving the transaction, then fetching and refreshing the account details for the origin account, and finally creating a {@link TransactionOutbound} object containing the details of the transaction. It includes error handling for various stages of the process.
     * </p>
     *
     * @param transactionInbound The data required to process the transaction. This cannot be null.
     * @return A {@link TransactionOutbound} object containing details of the transaction including the sender's account number, beneficiary's account number, and transfer status.
     * @throws IllegalArgumentException if the origin account number provided in {@code transactionInbound} does not correspond to an existing account.
     * @throws RuntimeException if an unexpected error occurs during the transaction processing or when fetching account details.
     * @see TransactionInbound
     * @see TransactionOutbound
     */
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


    /**
     * Saves a transaction based on the provided {@link TransactionInbound} data.
     * <p>
     * This method performs several steps to save a transaction:
     * <ul>
     * <li>Fetches the account associated with the origin account number from the repository.</li>
     * <li>Fetches the transaction type from the repository based on the provided transaction type ID.</li>
     * <li>Checks if there are sufficient funds in the account for the transaction.</li>
     * <li>Updates the account balance after deducting the transaction amount.</li>
     * <li>Creates a new {@link Transaction} entity, sets its properties, and saves it to the repository.</li>
     * </ul>
     * <p>
     * The method includes error handling for each stage of the process and throws exceptions if any issues are encountered.
     * </p>
     *
     * @param transactionInbound The data required to create and save a new transaction. This cannot be null.
     * @return The saved {@link Transaction} entity.
     * @throws IllegalArgumentException if the origin account number or transaction type ID is invalid, or if there are insufficient funds for the transaction.
     * @throws RuntimeException if there is an error fetching the account or transaction type, updating the account balance, or saving the transaction.
     * @see TransactionInbound
     * @see Transaction
     */
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
