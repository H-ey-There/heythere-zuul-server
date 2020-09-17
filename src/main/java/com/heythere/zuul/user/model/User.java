package com.heythere.zuul.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heythere.zuul.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    @Column(nullable = true)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(nullable = true)
    private String providerId;

    @Builder
    public User(
                Long id,
                String name,
                @Email String email,
                String imageUrl,
                Boolean emailVerified,
                String password,
                @NotNull AuthProvider provider,
                String providerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.emailVerified = emailVerified;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }
}
