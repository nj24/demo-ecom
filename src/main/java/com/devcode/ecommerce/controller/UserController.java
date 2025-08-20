package com.devcode.ecommerce.controller;


import com.devcode.ecommerce.entity.User;
import com.devcode.ecommerce.model.UserDto;
import com.devcode.ecommerce.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "basicAuth")
public class UserController {

    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.name(), userDto.email(), userDto.password(), userDto.username());
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public UserDto loginUser(@RequestBody UserDto userDto) {
        return null;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
