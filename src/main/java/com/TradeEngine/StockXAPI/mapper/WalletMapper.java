package com.TradeEngine.StockXAPI.mapper;

import com.TradeEngine.StockXAPI.dto.WalletDTO;
import com.TradeEngine.StockXAPI.model.Wallet;

public class WalletMapper {
    public static WalletDTO toDTO(Wallet wallet) {

        return WalletDTO.builder()
                .id(wallet.getId())
                .userId(wallet.getUser().getId())
                .balance(wallet.getBalance())
                .build();    }
}
