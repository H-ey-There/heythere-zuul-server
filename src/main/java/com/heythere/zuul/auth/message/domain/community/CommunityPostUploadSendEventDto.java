package com.heythere.zuul.auth.message.domain.community;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityPostUploadSendEventDto {
    private Integer userId;
    private CommunityPostEventValue value;

    @Builder
    public CommunityPostUploadSendEventDto(Integer userId, CommunityPostEventValue value) {
        this.userId = userId;
        this.value = value;
    }
}
