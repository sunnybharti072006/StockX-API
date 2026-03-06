package com.TradeEngine.StockXAPI.controller;

import com.TradeEngine.StockXAPI.model.Order;
import com.TradeEngine.StockXAPI.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // BUY STOCK
    @PostMapping("/buy")
    public Order buyStock(
            @RequestParam Long userId,
            @RequestParam Long stockId,
            @RequestParam Long quantity
    ){
        return orderService.buyStock(userId, stockId, quantity);
    }

    // SELL STOCK
    @PostMapping("/sell")
    public Order sellStock(
            @RequestParam Long userId,
            @RequestParam Long stockId,
            @RequestParam Long quantity
    ){
        return orderService.sellStock(userId, stockId, quantity);
    }
}