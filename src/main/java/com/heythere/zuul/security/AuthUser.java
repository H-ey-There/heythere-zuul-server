package com.heythere.zuul.security;

import com.heythere.zuul.user.model.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class AuthUser implements OAuth2User, UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Builder
    public AuthUser(final Long id,
                    final String email,
                    final String password,
                    final Collection<? extends GrantedAuthority> authorities,
                    Map<String, Object> attributes) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static AuthUser create(final User user) {
        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return AuthUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    public static AuthUser create(final User user, final Map<String, Object> attributes) {
        return AuthUser.create(user)
                .withAttribute(attributes);
    }

    private AuthUser withAttribute(final Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
