package com.TradeEngine.StockXAPI.service;

import com.TradeEngine.StockXAPI.model.*;
import com.TradeEngine.StockXAPI.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final WalletRepository walletRepository;
    private final PortfolioRepository portfolioRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        StockRepository stockRepository,
                        WalletRepository walletRepository,
                        PortfolioRepository portfolioRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
        this.walletRepository = walletRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @Transactional
    public Order buyStock(Long userId, Long stockId, Long quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BigDecimal totalPrice = stock.getPrice().multiply(BigDecimal.valueOf(quantity));

        if (wallet.getBalance().compareTo(totalPrice) < 0)
            throw new RuntimeException("Insufficient balance");

        wallet.setBalance(wallet.getBalance().subtract(totalPrice));
        walletRepository.save(wallet);

        // Update Portfolio logic
        Portfolio portfolio = portfolioRepository
                .findByUserIdAndStockId(userId, stockId)
                .orElse(null);

        if (portfolio == null) {
            portfolio = Portfolio.builder()
                    .user(user)
                    .stock(stock)
                    .quantity(quantity)
                    .build();
        } else {
            portfolio.setQuantity(portfolio.getQuantity() + quantity);
        }
        portfolioRepository.save(portfolio);

        // FIX: Builder methods must match Order.java field names (userId, stockId)
        Order order = Order.builder()
                .userId(userId)    // pehle yahan .user(user) tha, isliye crash ho raha tha
                .stockId(stockId)  // pehle yahan .stock(stock) tha
                .quantity(quantity)
                .price(stock.getPrice())
                .type(OrderType.BUY)
                .status("COMPLETED")
                .build();

        return orderRepository.save(order);
    }

    @Transactional
    public Order sellStock(Long userId, Long stockId, Long quantity) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        Portfolio portfolio = portfolioRepository
                .findByUserIdAndStockId(userId, stockId)
                .orElseThrow(() -> new RuntimeException("Stock not owned"));

        if (portfolio.getQuantity() < quantity)
            throw new RuntimeException("Not enough stock");

        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        portfolioRepository.save(portfolio);

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BigDecimal totalGain = stock.getPrice().multiply(BigDecimal.valueOf(quantity));
        wallet.setBalance(wallet.getBalance().add(totalGain));
        walletRepository.save(wallet);

        // FIX: Builder methods matching Order.java field names

        Order order = Order.builder()
                .userId(userId)
                .stockId(stockId)
                .quantity(quantity)
                .price(stock.getPrice())
                .type(OrderType.SELL)
                .status("COMPLETED")
                .build();

        return orderRepository.save(order);
    }
}