package com.heythere.zuul.auth.security.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginFormRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public LoginFormRequest(@NotBlank @Email String email, @NotBlank String password) {
        this.email = email;
        this.password = password;
    }
}