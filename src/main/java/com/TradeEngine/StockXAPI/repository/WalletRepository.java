package com.TradeEngine.StockXAPI.repository;

import java.util.Optional;

import com.TradeEngine.StockXAPI.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserId(Long userId);

}