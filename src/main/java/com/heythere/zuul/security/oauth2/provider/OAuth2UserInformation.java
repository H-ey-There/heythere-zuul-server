package com.heythere.zuul.security.oauth2.provider;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class OAuth2UserInformation<T extends Map<String, Object>> {
    protected T attributes;
    public OAuth2UserInformation(T attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
}
