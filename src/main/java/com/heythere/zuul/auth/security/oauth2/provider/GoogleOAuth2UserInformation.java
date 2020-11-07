package com.heythere.zuul.auth.security.oauth2.provider;

import java.util.Map;


public class GoogleOAuth2UserInformation<T extends Map<String, Object>> extends OAuth2UserInformation<T> {

    public GoogleOAuth2UserInformation(T attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
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
        return (String) attributes.get("picture");
    }
}
