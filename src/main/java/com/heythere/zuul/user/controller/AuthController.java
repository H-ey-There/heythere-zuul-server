package com.heythere.zuul.user.controller;

import com.heythere.zuul.security.jwt.TokenProvider;
import com.heythere.zuul.security.payload.ApiResponse;
import com.heythere.zuul.security.payload.AuthResponse;
import com.heythere.zuul.security.payload.LoginFormRequest;
import com.heythere.zuul.security.payload.SignUpRequest;
import com.heythere.zuul.user.model.User;
import com.heythere.zuul.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginFormRequest payload) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        payload.getEmail(),
                        payload.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody final SignUpRequest payload) {

        final User newUser = userService.registerUser(payload);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/user/me")
                .buildAndExpand(newUser.getId())
                .toUri();
        log.info(location.toString());
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }
}
