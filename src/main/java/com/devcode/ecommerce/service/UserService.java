package com.devcode.ecommerce.service;

import com.devcode.ecommerce.entity.User;
import com.devcode.ecommerce.model.UserDto;
import com.devcode.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final AuthenticationProvider authenticationProvider;
    private final JWTService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationProvider authenticationProvider, JWTService jwtService) {
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
    }

    public User saveUser(UserDto userDto) {
        User user = new User(userDto.name(), userDto.email(), passwordEncoder.encode(userDto.password()), userDto.username());
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String authenticateUser(String username, String password) {
        Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
       if( authenticate.isAuthenticated()){
           return jwtService.generateJWTToken(username,password);
       }
       else {
           return "Failed login";
       }
    }
}
