package com.TradeEngine.StockXAPI.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderDTO {

    private Long orderId;
    private String stockSymbol;
    private Long quantity;
    private BigDecimal price;
    private String type;
    private String status;

}