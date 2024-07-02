package com.app.bankservice.entity;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table (name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String accountNumber;
    private Double balance;
    private String status;
    private Double minBalance;
    private Double maxTransactionAmount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToMany(mappedBy = "accounts")
    private Set<User> users;

    @OneToMany(targetEntity = Transaction.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "accountId")
    private List<Transaction> transactions;

    @OneToMany(targetEntity = BankCard.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "accountId")
    private List<BankCard> bankCards;

    public Account() {
    }

    public Account(Long accountId, String accountNumber, Double balance, String status, Double minBalance, Double maxTransactionAmount, Date createdDate, Set<User> users, List<Transaction> transactions, List<BankCard> bankCards) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.minBalance = minBalance;
        this.maxTransactionAmount = maxTransactionAmount;
        this.createdDate = createdDate;
        this.users = users;
        this.transactions = transactions;
        this.bankCards = bankCards;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(Double minBalance) {
        this.minBalance = minBalance;
    }

    public Double getMaxTransactionAmount() {
        return maxTransactionAmount;
    }

    public void setMaxTransactionAmount(Double maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<BankCard> getBankCards() {
        return bankCards;
    }

    public void setBankCards(List<BankCard> bankCards) {
        this.bankCards = bankCards;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                ", minBalance=" + minBalance +
                ", maxTransactionAmount=" + maxTransactionAmount +
                ", createdDate=" + createdDate +
                ", users=" + users +
                ", transactions=" + transactions +
                ", bankCards=" + bankCards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(balance, account.balance) && Objects.equals(status, account.status) && Objects.equals(minBalance, account.minBalance) && Objects.equals(maxTransactionAmount, account.maxTransactionAmount) && Objects.equals(createdDate, account.createdDate) && Objects.equals(users, account.users) && Objects.equals(transactions, account.transactions) && Objects.equals(bankCards, account.bankCards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountNumber, balance, status, minBalance, maxTransactionAmount, createdDate, users, transactions, bankCards);
    }
}
