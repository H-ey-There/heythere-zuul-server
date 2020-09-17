package com.heythere.zuul.security.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
}