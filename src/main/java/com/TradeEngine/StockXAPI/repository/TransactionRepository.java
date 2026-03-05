package com.TradeEngine.StockXAPI.repository;

import com.TradeEngine.StockXAPI.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser_IdOrderByTimestampDesc(Long userId);
}