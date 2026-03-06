package com.TradeEngine.StockXAPI.controller;


import com.TradeEngine.StockXAPI.model.Stock;
import com.TradeEngine.StockXAPI.repository.StockRepository;
import com.TradeEngine.StockXAPI.service.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @PostMapping("/create")
    public Stock createStock(@RequestBody Stock stock){
        System.out.println(stock);
        return stockService.createStock(stock);
    }

    @GetMapping
    public List<Stock> getAllStocks(){
        return stockService.getAllStocks();
    }
}