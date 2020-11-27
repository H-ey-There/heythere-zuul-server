package com.heythere.zuul.auth.message.domain.video;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoUploadListenEventDto {
    private Integer userId;
    private VideoSnippet snippet;

    @Builder
    public VideoUploadListenEventDto(Integer userId, VideoSnippet snippet) {
        this.userId = userId;
        this.snippet = snippet;
    }
}
