package com.heythere.zuul.auth.message.domain.video;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoUploadSendEventDto {
    private Integer userId;
    private VideoEventValue value;

    @Builder
    public VideoUploadSendEventDto(Integer userId, VideoEventValue value) {
        this.userId = userId;
        this.value = value;
    }
}
