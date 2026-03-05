package com.TradeEngine.StockXAPI.mapper;
import com.TradeEngine.StockXAPI.dto.UserDTO;
import com.TradeEngine.StockXAPI.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}