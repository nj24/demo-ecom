package com.devcode.ecommerce.controller;


import com.devcode.ecommerce.entity.User;
import com.devcode.ecommerce.model.UserDto;
import com.devcode.ecommerce.model.UserLoginDetails;
import com.devcode.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "basicAuth")
public class UserController {

    private  final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserLoginDetails userLoginDetails) {
        return userService.authenticateUser(userLoginDetails.username(),userLoginDetails.password());
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

}
