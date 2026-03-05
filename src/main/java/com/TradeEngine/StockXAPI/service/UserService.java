package com.TradeEngine.StockXAPI.service;

import com.TradeEngine.StockXAPI.model.User;
import com.TradeEngine.StockXAPI.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email, String username, String password) {
        User user = User.builder()
                .name(name)
                .email(email)
                .username(username)
                .password(password)
                .role("USER")
                .build();

        return userRepository.save(user);
    }
}