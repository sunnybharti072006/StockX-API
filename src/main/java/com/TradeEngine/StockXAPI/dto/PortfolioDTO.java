package com.TradeEngine.StockXAPI.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortfolioDTO {

    private String stockSymbol;
    private String stockName;
    private Long quantity;

}
