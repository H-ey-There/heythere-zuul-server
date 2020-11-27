package com.heythere.zuul.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heythere.zuul.auth.dto.RegisterUserRequestUserDto;
import com.heythere.zuul.auth.security.jwt.TokenProvider;
import com.heythere.zuul.auth.security.payload.*;
import com.heythere.zuul.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginFormRequest payload) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        payload.getEmail(),
                        payload.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.createToken(authentication);
        final Long userId = tokenProvider.getUserIdFromToken(token);
        return ResponseEntity.ok(new AuthResponse(userId, token));
    }


    @PostMapping("register")
    public ResponseEntity<Long> register(@RequestBody final RegisterUserRequestUserDto payload) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(payload));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") final Long id) throws JsonProcessingException {
        userService.deleteUserById(id);
        return ResponseEntity.status(200).body("user delete successfully!");
    }
}
