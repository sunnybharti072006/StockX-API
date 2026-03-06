package com.TradeEngine.StockXAPI.service;


import com.TradeEngine.StockXAPI.model.MarketNews;
import com.TradeEngine.StockXAPI.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketNewsService {
    private final NewsRepository newsRepository;

    public MarketNewsService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    public MarketNews createNews(MarketNews news){
        return newsRepository.save(news);
    }

    public List<MarketNews> getAllNews(){
        return newsRepository.findAll();
    }

}
