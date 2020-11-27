package com.heythere.zuul.auth.message.domain.video;

import com.heythere.zuul.auth.mapper.UserResponseMapper;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class VideoEventValue {
    private VideoSnippet snippet;
    private List<UserResponseMapper> subscribers;

    @Builder
    public VideoEventValue(VideoSnippet snippet, List<UserResponseMapper> subscribers) {
        this.snippet = snippet;
        this.subscribers = subscribers;
    }
}
