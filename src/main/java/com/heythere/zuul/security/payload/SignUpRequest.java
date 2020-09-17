package com.heythere.zuul.security.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SignUpRequest {
    private String name;
    @Email
    private String email;
    private String password;

    public SignUpRequest(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
