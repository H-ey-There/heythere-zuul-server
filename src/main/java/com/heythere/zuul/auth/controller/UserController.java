package com.heythere.zuul.auth.controller;

import com.heythere.zuul.auth.security.AuthUser;
import com.heythere.zuul.auth.security.Authentication;
import com.heythere.zuul.auth.security.exception.ResourceNotFoundException;
import com.heythere.zuul.auth.model.User;
import com.heythere.zuul.auth.repository.UserRepository;
import com.heythere.zuul.auth.security.payload.AuthUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("greeting/{name}")
    public String greeting(@PathVariable("name") final String name) {
        return "hello " + name;
    }

    @GetMapping("user/me")
    @PreAuthorize("hasRole('USER')")
    public AuthUserResponseDto getCurrentUser(@Authentication final AuthUser authUser) {
        final User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", authUser.getId()));

        return AuthUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImageUrl())
                .build();
    }
}