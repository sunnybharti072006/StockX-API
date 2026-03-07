package com.TradeEngine.StockXAPI.controller;

import com.TradeEngine.StockXAPI.model.Transaction;
import com.TradeEngine.StockXAPI.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
 
    //Get The Transaction History 
    @GetMapping("/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return transactionRepository.findByUser_IdOrderByTimestampDesc(userId);
    }
}