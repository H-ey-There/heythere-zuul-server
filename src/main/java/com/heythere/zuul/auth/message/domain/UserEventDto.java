package com.heythere.zuul.auth.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEventDto {
    private Integer userEventId;
    private UserMessageDto userMessageDto;
}
