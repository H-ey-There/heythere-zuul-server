package com.heythere.zuul.auth.service;

import com.heythere.zuul.auth.dto.SubscribeRequestDto;
import com.heythere.zuul.auth.mapper.SubscribeStatusResponseMapper;
import com.heythere.zuul.auth.mapper.UserResponseMapper;
import com.heythere.zuul.auth.model.Subscriber;
import com.heythere.zuul.auth.model.User;
import com.heythere.zuul.auth.repository.SubscriberRepository;
import com.heythere.zuul.auth.repository.UserRepository;
import com.heythere.zuul.auth.security.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubscribeServiceImpl implements SubscribeService {
    private final SubscriberRepository subscriberRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public SubscribeStatusResponseMapper subscribeStatus(final SubscribeRequestDto payload) {

        return SubscribeStatusResponseMapper
                .builder()
                .subscriberId(payload.getRequestUserId())
                .targetUserId(payload.getTargetUserId())
                .subscribe(getCurrentSubscribeStatus(payload))
                .build();
    }

    @Override
    @Transactional
    public SubscribeStatusResponseMapper subscribeButtonClick(final SubscribeRequestDto payload) {
        final Boolean status = getCurrentSubscribeStatus(payload);

        if (!status) {
            subscriberRepository.save(
                    Subscriber.builder()
                    .subscriberUserId(payload.getRequestUserId())
                    .targetUserId(payload.getTargetUserId())
                    .build());
            return SubscribeStatusResponseMapper
                    .builder()
                    .subscriberId(payload.getRequestUserId())
                    .targetUserId(payload.getTargetUserId())
                    .subscribe(true).build();
        }

        subscriberRepository.deleteById(
                subscriberRepository.getSubscriberMappingId(payload.getRequestUserId(), payload.getTargetUserId())
        );

        return SubscribeStatusResponseMapper
                .builder()
                .subscriberId(payload.getRequestUserId())
                .targetUserId(payload.getTargetUserId())
                .subscribe(false).build();
    }

    @Override
    @Transactional
    public List<UserResponseMapper> retrieveAllMySubscriber(Long myId) {
        return subscriberRepository.findAllByTargetUserId(myId)
                .stream()
                .map(s -> {
                    final User subscriber = userRepository.findById(s.getSubscriberUserId())
                            .orElseThrow(() -> new ResourceNotFoundException("User","id",s.getSubscriberUserId()));
                    return UserResponseMapper.of(subscriber);
                })
                .collect(Collectors.toList());
    }


    private Boolean getCurrentSubscribeStatus(final SubscribeRequestDto payload) {
        return subscriberRepository.existsBySubscriberUserIdAndAndTargetUserId(payload.getRequestUserId(), payload.getTargetUserId());
    }
}
