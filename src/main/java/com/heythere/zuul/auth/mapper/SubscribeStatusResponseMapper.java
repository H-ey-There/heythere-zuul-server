package com.heythere.zuul.auth.mapper;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubscribeStatusResponseMapper {
    private Long targetUserId;
    private Long subscriberId;
    private Boolean subscribe;
}
