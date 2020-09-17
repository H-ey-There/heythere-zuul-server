package com.heythere.zuul.security.oauth2.provider;

import java.util.Map;

public class GithubOAuth2UserInformation<T extends Map<String, Object>> extends OAuth2UserInformation<T> {

    public GithubOAuth2UserInformation(T attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}
