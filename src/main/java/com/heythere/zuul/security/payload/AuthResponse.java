package com.heythere.zuul.security.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    private final String accessToken;
    private String tokenType = "Bearer";
}