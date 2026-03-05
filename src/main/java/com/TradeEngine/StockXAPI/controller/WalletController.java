package com.TradeEngine.StockXAPI.controller;


import com.TradeEngine.StockXAPI.dto.DepositRequest;
import com.TradeEngine.StockXAPI.dto.TransactionDTO;
import com.TradeEngine.StockXAPI.dto.WalletDTO;
import com.TradeEngine.StockXAPI.dto.WithdrawRequest;
import com.TradeEngine.StockXAPI.mapper.WalletMapper;
import com.TradeEngine.StockXAPI.model.Wallet;
import com.TradeEngine.StockXAPI.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // Controller
    @PostMapping("/create/{userId}")
    public WalletDTO createWallet(@PathVariable Long userId) {
        Wallet wallet = walletService.createWallet(userId);
        return WalletMapper.toDTO(wallet);
    }

    @PostMapping("/deposit")
    public WalletDTO deposit(@RequestBody DepositRequest request) {
        Wallet wallet = walletService.deposit(
                request.getUserId(), request.getAmount()
        );
        return WalletMapper.toDTO(wallet);
    }
    @PostMapping("/withdraw")
    public WalletDTO withdraw(@RequestBody WithdrawRequest request) {
        Wallet wallet = walletService.withdraw(request.getUserId(), request.getAmount());
        return WalletMapper.toDTO(wallet);
    }
    @GetMapping("/balance/{userId}")
    public BigDecimal getBalance(@PathVariable Long userId) {

        return walletService.getBalance(userId);
    }
    @GetMapping("/transactions")
    public List<TransactionDTO> transactions(@RequestParam Long userId) {
        return walletService.getTransactionHistory(userId);
    }
}
