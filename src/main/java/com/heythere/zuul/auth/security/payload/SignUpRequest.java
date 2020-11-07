package com.heythere.zuul.auth.security.payload;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@Getter
public class SignUpRequest {
    @NotBlank
    private final String name;
    @Email
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;

    @ConstructorProperties({"name", "email", "password"})
    public SignUpRequest(String name, @Email String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
