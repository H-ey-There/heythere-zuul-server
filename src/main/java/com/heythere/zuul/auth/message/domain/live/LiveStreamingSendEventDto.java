package com.heythere.zuul.auth.message.domain.live;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiveStreamingSendEventDto {
    private Integer userId;
    private LiveStreamingEventValue value;

    @Builder
    public LiveStreamingSendEventDto(Integer userId, LiveStreamingEventValue value) {
        this.userId = userId;
        this.value = value;
    }
}
