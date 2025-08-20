package com.devcode.ecommerce.config;

import com.devcode.ecommerce.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    public CustomAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        return userRepository.getByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> new UsernamePasswordAuthenticationToken(
                        user,                  // principal
                        password,              // credentials
                        authentication.getAuthorities() // roles/authorities
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found or invalid password"));

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
