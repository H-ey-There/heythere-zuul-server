package com.heythere.zuul.auth.message.domain.community;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityPostUploadListenerEventDto {
    private Integer userId;
    private CommunityPostSnippet snippet;

    @Builder
    public CommunityPostUploadListenerEventDto(Integer userId, CommunityPostSnippet snippet) {
        this.userId = userId;
        this.snippet = snippet;
    }
}
