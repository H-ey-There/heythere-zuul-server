package com.heythere.zuul.auth.security.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    private final Long id;
    private final String accessToken;
    private String tokenType = "Bearer";
}