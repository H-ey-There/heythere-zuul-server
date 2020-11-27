package com.heythere.zuul.auth.mapper;


import com.heythere.zuul.auth.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseMapper {
    private final Long id;
    private final String email;
    private final String name;
    private final String img;

    @Builder
    public UserResponseMapper(Long id, String email, String name, String img) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
    }

    public static UserResponseMapper of(final User user) {
        return UserResponseMapper.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImageUrl())
                .build();
    }
}

