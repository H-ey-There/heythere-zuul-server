package com.heythere.zuul.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RegisterUserRequestUserDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @Builder
    public RegisterUserRequestUserDto(@NotNull @Email String email,
                                      @NotNull String name,
                                      @NotNull String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}