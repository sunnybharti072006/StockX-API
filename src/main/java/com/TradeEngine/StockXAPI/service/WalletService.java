package com.TradeEngine.StockXAPI.service;

import com.TradeEngine.StockXAPI.dto.TransactionDTO;
import com.TradeEngine.StockXAPI.model.*;
import com.TradeEngine.StockXAPI.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository,
                         UserRepository userRepository,
                         TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // Create wallet
    @Transactional
    public Wallet createWallet(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();

        return walletRepository.save(wallet);
    }

    // Deposit money
    @Transactional
    public Wallet deposit(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("Amount must be greater than zero");

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        // Save transaction
        Transaction tx = Transaction.builder()
                .user(wallet.getUser())
                .wallet(wallet)
                .amount(amount)
                .type(TransactionType.DEPOSIT)
                .timestamp(LocalDateTime.now())
                .status("SUCCESS")
                .build();

        Transaction savedTx = transactionRepository.save(tx);
        System.out.println("Transaction saved with ID: " + savedTx.getId());

        return wallet;
    }

    // Withdraw money
    @Transactional
    public Wallet withdraw(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("Amount must be greater than zero");

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance().compareTo(amount) < 0)
            throw new RuntimeException("Insufficient balance");

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        // Save transaction
        Transaction tx = Transaction.builder()
                .user(wallet.getUser())
                .wallet(wallet)
                .amount(amount)
                .type(TransactionType.WITHDRAW)
                .timestamp(LocalDateTime.now())
                .status("SUCCESS")
                .build();

        Transaction savedTx = transactionRepository.save(tx);
        System.out.println("Transaction saved with ID: " + savedTx.getId());  // Debug

        return wallet;
    }

    // Get balance
    public BigDecimal getBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }

    // Get transaction history
    public List<TransactionDTO> getTransactionHistory(Long userId) {
        if (!userRepository.existsById(userId))
            throw new RuntimeException("User not found");

        List<Transaction> transactions = transactionRepository
                .findByUser_IdOrderByTimestampDesc(userId);

        return transactions.stream()
                .map(tx -> TransactionDTO.builder()
                        .amount(tx.getAmount())
                        .type(tx.getType().name())
                        .status(tx.getStatus())
                        .timestamp(tx.getTimestamp())
                        .build())
                .toList();
    }
}