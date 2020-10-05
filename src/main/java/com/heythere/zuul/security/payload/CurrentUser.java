package com.heythere.zuul.security.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CurrentUser {
    private Long id;
    private String email;
    private String name;
    private String img;

    @Builder
    public CurrentUser(Long id, String email, String name, String img) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
    }
}
