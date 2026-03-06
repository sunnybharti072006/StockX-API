package com.TradeEngine.StockXAPI.service;


import com.TradeEngine.StockXAPI.model.OrderType;
import com.TradeEngine.StockXAPI.model.Stock;
import com.TradeEngine.StockXAPI.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }

    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }

    public void updatePrice(Stock stock, OrderType type, Long quantity){
        BigDecimal price = stock.getPrice();
        BigDecimal change = price.multiply(BigDecimal.valueOf(quantity))
                .multiply(BigDecimal.valueOf(0.001));

        if (type.equals(OrderType.BUY)){
            price = price.add(change);
        }else {
            price = price.subtract(change);
        }
        stock.setPrice(price);
        stockRepository.save(stock);
    }

}