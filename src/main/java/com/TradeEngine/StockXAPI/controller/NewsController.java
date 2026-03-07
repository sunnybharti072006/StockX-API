package com.TradeEngine.StockXAPI.controller;


import com.TradeEngine.StockXAPI.model.MarketNews;
import com.TradeEngine.StockXAPI.repository.NewsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }
    //Post the News With PostMan
    @PostMapping("/create")
    public  MarketNews createNews(@RequestBody MarketNews news){
        return newsRepository.save(news);
    }
    //Get news
    @GetMapping
    public List<MarketNews> getAllNews(){
        return newsRepository.findAll();
    }

}

