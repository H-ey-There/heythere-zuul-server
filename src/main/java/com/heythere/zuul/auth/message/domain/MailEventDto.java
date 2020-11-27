package com.heythere.zuul.auth.message.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MailEventDto {
    private final Integer userId;
    private final String email;
    private final String name;

    @Builder
    public MailEventDto(Integer userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}
