package com.TradeEngine.StockXAPI.repository;
import com.TradeEngine.StockXAPI.model.MarketNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<MarketNews, Long> {
}
