package com.TradeEngine.StockXAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long userId;
    private Long stockId;
    private Long quantity;

}