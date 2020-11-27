package com.heythere.zuul.auth.message.domain.video;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoSnippet {
    private String title;
    private String description;
    private String thumbnail;
    private String name;
    private String userImg;

    @Builder
    public VideoSnippet(String title, String description, String thumbnail, String name, String userImg) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.name = name;
        this.userImg = userImg;
    }
}
