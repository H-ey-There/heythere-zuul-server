package com.heythere.zuul.auth.security.payload;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthUserResponseDto {
    private Long id;
    private String email;
    private String name;
    private String img;
}
