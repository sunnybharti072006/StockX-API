package com.TradeEngine.StockXAPI.service;


import com.TradeEngine.StockXAPI.model.Portfolio;
import com.TradeEngine.StockXAPI.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository){
        this.portfolioRepository = portfolioRepository;
    }
    public List<Portfolio> getUserPortfolio(Long userId){
        return portfolioRepository.findByUserId(userId);
    }
}
