package com.heythere.zuul.auth.model;

import com.heythere.zuul.auth.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Subscriber extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscribe_id")
    private Long id;

    private Long subscriberUserId;
    private Long targetUserId;

    @Builder
    public Subscriber(Long id, Long subscriberUserId, Long targetUserId) {
        this.id = id;
        this.subscriberUserId = subscriberUserId;
        this.targetUserId = targetUserId;
    }
}
