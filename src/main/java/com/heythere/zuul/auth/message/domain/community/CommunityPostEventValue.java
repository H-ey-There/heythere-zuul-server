package com.heythere.zuul.auth.message.domain.community;

import com.heythere.zuul.auth.mapper.UserResponseMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommunityPostEventValue {
    private CommunityPostSnippet snippet;
    private List<UserResponseMapper> subscribers;

    @Builder
    public CommunityPostEventValue(CommunityPostSnippet snippet, List<UserResponseMapper> subscribers) {
        this.snippet = snippet;
        this.subscribers = subscribers;
    }
}
