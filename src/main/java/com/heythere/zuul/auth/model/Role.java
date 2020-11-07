package com.heythere.zuul.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER", "일반 사용자"),
    GUEST("ROLE_GUEST", "손님"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
