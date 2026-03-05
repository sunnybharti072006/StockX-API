package com.TradeEngine.StockXAPI.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {

    private BigDecimal amount;
    private String type;
    private String status;
    private LocalDateTime timestamp;
}