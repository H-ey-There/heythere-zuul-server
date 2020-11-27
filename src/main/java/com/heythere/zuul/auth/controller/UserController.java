package com.heythere.zuul.auth.controller;

import com.heythere.zuul.auth.mapper.UserResponseMapper;
import com.heythere.zuul.auth.security.AuthUser;
import com.heythere.zuul.auth.security.Authentication;
import com.heythere.zuul.auth.security.exception.ResourceNotFoundException;
import com.heythere.zuul.auth.model.User;
import com.heythere.zuul.auth.repository.UserRepository;
import com.heythere.zuul.auth.security.payload.AuthUserResponseDto;
import com.heythere.zuul.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
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

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public UserResponseMapper updateImg(@RequestParam("requestUserId") final Long id,
                                        @RequestParam("img") final MultipartFile file) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        return userService.updateImg(id, file);
    }

}