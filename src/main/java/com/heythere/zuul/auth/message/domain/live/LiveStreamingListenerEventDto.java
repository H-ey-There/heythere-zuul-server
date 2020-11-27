package com.heythere.zuul.auth.message.domain.live;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiveStreamingListenerEventDto {
    private Integer userId;
    private LiveStreamingSnippet snippet;

    @Builder
    public LiveStreamingListenerEventDto(Integer userId, LiveStreamingSnippet snippet) {
        this.userId = userId;
        this.snippet = snippet;
    }
}
