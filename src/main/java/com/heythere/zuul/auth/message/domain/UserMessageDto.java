package com.heythere.zuul.auth.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserMessageDto {
    private String email;
    private String name;
    private String img;
    private String password;
}
