package com.TradeEngine.StockXAPI.dto;



import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawRequest {

    private Long userId;
    private BigDecimal amount;
}
