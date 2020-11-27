package com.heythere.zuul.auth.service;

import com.heythere.zuul.auth.mapper.SubscribeStatusResponseMapper;
import com.heythere.zuul.auth.mapper.UserResponseMapper;

import java.util.List;

public interface SubscribeService {
    SubscribeStatusResponseMapper subscribeStatus(final Long requestUserId,
                                                  final Long targetUserId);
    SubscribeStatusResponseMapper subscribeButtonClick(final Long requestUserId,
                                                       final Long targetUserId);
    List<UserResponseMapper> retrieveAllMySubscriber(final Long myId);
}
