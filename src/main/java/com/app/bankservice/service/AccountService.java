package com.app.bankservice.service;

import com.app.bankservice.entity.Account;
import com.app.bankservice.entity.BankCard;
import com.app.bankservice.entity.User;
import com.app.bankservice.model.AccountDTO;
import com.app.bankservice.model.AccountResponseDTO;
import com.app.bankservice.model.BankCardDTO;
import com.app.bankservice.repository.AccountRepository;
import com.app.bankservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AccountService {

    private final UserRepository userRepository;

    @Autowired
    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public AccountResponseDTO getAccountDetailsByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        User user;
        try {
            user = userRepository.findByUsername(username);
            if (user == null) {
                throw new IllegalArgumentException("User not found for username: " + username);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user details for username: " + username, e);
        }

        Set<Account> accounts = user.getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            throw new IllegalArgumentException("No accounts found for user: " + username);
        }

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        List<AccountDTO> accountDTOList = new ArrayList<>();

        for (Account account : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            try {
                accountDTO.setAccountNumber(account.getAccountNumber());
                accountDTO.setBalance(account.getBalance());
                accountDTO.setStatus(account.getStatus());
                accountDTO.setCreatedDate(account.getCreatedDate());
                accountDTO.setAccountType(account.getAccountType().getAccountTypeName());
                accountDTO.setCurrencyType(account.getCurrencyType().getCurrencyName());

                List<BankCard> bankCards = account.getBankCards();
                if (bankCards != null && !bankCards.isEmpty()) {
                    BankCard bankCard = bankCards.get(0);
                    BankCardDTO bankCardDTO = new BankCardDTO();
                    bankCardDTO.setCardNumber(bankCard.getCardNumber());
                    bankCardDTO.setCreditLimit(bankCard.getCreditLimit());
                    bankCardDTO.setStatus(bankCard.getStatus());
                    bankCardDTO.setExpiryDate(bankCard.getExpiryDate());
                    bankCardDTO.setCardType(bankCard.getCardType().getTypeName());
                    accountDTO.setBankCard(bankCardDTO);
                }
                accountDTOList.add(accountDTO);
            } catch (Exception e) {
                throw new RuntimeException("Error processing account details for account number: " + account.getAccountNumber(), e);
            }
        }

        accountResponseDTO.setFirstName(user.getFirstName());
        accountResponseDTO.setLastName(user.getLastName());
        accountResponseDTO.setAccountDTOList(accountDTOList);

        return accountResponseDTO;
    }
}
