package com.heythere.zuul.auth.service;

import com.heythere.zuul.auth.dto.SubscribeRequestDto;
import com.heythere.zuul.auth.mapper.SubscribeStatusResponseMapper;
import com.heythere.zuul.auth.mapper.UserResponseMapper;

import java.util.List;

public interface SubscribeService {
    SubscribeStatusResponseMapper subscribeStatus(final SubscribeRequestDto payload);
    SubscribeStatusResponseMapper subscribeButtonClick(final SubscribeRequestDto payload);
    List<UserResponseMapper> retrieveAllMySubscriber(final Long myId);
}
