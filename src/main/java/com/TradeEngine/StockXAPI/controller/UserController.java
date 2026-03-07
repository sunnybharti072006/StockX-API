package com.TradeEngine.StockXAPI.controller;

import com.TradeEngine.StockXAPI.dto.CreateUserRequest;
import com.TradeEngine.StockXAPI.dto.UserDTO;
import com.TradeEngine.StockXAPI.mapper.UserMapper;
import com.TradeEngine.StockXAPI.model.User;
import com.TradeEngine.StockXAPI.repository.UserRepository;
import com.TradeEngine.StockXAPI.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //Create The User or Post the User details to api with Postman api

    @PostMapping("/create")
    public User createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(
                request.getName(),
                request.getEmail(),
                request.getUsername(),
                request.getPassword()
        );
    }
    
    //Get All user
    @GetMapping("/alluser")
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}
