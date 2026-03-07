package com.TradeEngine.StockXAPI.controller;

import com.TradeEngine.StockXAPI.model.Portfolio;
import com.TradeEngine.StockXAPI.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")

public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService){
        this.portfolioService = portfolioService;
    }

    //Get The User Portfolio via UserId
    @GetMapping("/{userId}")
    public List<Portfolio> getPortfolio(@PathVariable Long userId){
        return portfolioService.getUserPortfolio(userId);
    }
}
