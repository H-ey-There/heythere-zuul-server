package com.heythere.zuul.auth.message.domain.live;

import com.heythere.zuul.auth.mapper.UserResponseMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LiveStreamingEventValue {
    private LiveStreamingSnippet snippet;
    private List<UserResponseMapper> subscribers;

    @Builder
    public LiveStreamingEventValue(LiveStreamingSnippet snippet, List<UserResponseMapper> subscribers) {
        this.snippet = snippet;
        this.subscribers = subscribers;
    }
}
