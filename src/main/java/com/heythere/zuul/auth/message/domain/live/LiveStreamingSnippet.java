package com.heythere.zuul.auth.message.domain.live;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiveStreamingSnippet {
    private String roomTitle;
    private String name;
    private String userImg;

    @Builder
    public LiveStreamingSnippet(String roomTitle, String name, String userImg) {
        this.roomTitle = roomTitle;
        this.name = name;
        this.userImg = userImg;
    }
}
