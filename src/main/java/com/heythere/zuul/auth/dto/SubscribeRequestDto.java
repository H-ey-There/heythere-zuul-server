package com.heythere.zuul.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class SubscribeRequestDto {
    private Long requestUserId;
    private Long targetUserId;
}
