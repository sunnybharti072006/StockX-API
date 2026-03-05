package com.TradeEngine.StockXAPI.controller;

import com.TradeEngine.StockXAPI.dto.CreateUserRequest;
import com.TradeEngine.StockXAPI.model.User;
import com.TradeEngine.StockXAPI.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/create")
    public User createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(
                request.getName(),
                request.getEmail(),
                request.getUsername(),
                request.getPassword()
        );
    }

}
