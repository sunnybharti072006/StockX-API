package com.TradeEngine.StockXAPI.service;

import com.TradeEngine.StockXAPI.model.MarketTrend;
import com.TradeEngine.StockXAPI.model.Stock;
import com.TradeEngine.StockXAPI.repository.StockRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
public class MarketSimulationService {

    private final StockRepository stockRepository;
    private final SimpMessagingTemplate messagingTemplate; // For real-time push
    private final Random random = new Random();
    private MarketTrend marketTrend = MarketTrend.NEUTRAL;

    public MarketSimulationService(StockRepository stockRepository, SimpMessagingTemplate messagingTemplate) {
        this.stockRepository = stockRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void simulateMarket() {
        List<Stock> stocks = stockRepository.findAll();

        // Trend Logic
        int trendChance = random.nextInt(100);
        if(trendChance < 30) marketTrend = MarketTrend.BULL;
        else if(trendChance < 60) marketTrend = MarketTrend.BEAR;
        else marketTrend = MarketTrend.NEUTRAL;

        for(Stock stock : stocks){
            BigDecimal price = stock.getPrice();
            double changePercent = random.nextDouble() * 0.02;

            if(marketTrend == MarketTrend.BULL) changePercent += 0.01;
            if(marketTrend == MarketTrend.BEAR) changePercent -= 0.01;

            boolean buyPressure = random.nextBoolean();
            BigDecimal change = price.multiply(BigDecimal.valueOf(changePercent));
            if(!buyPressure) change = change.negate();

            BigDecimal newPrice = price.add(change);

            // Circuit breaker (10%)
            BigDecimal maxChange = price.multiply(BigDecimal.valueOf(0.10));
            if(newPrice.subtract(price).abs().compareTo(maxChange) > 0){
                newPrice = price.add(maxChange.multiply(newPrice.compareTo(price) > 0 ? BigDecimal.ONE : BigDecimal.valueOf(-1)));
            }

            newPrice = newPrice.setScale(2, RoundingMode.HALF_UP);
            stock.setPrice(newPrice);
            stockRepository.save(stock);

            messagingTemplate.convertAndSend("/topic/prices", stock);
        }
        System.out.println("Market updated | Trend: " + marketTrend);
    }
}