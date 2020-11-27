package com.heythere.zuul.auth.service;

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
    public SubscribeStatusResponseMapper subscribeStatus(final Long requestUserId,
                                                         final Long targetUserId) {

        return SubscribeStatusResponseMapper
                .builder()
                .subscriberId(requestUserId)
                .targetUserId(targetUserId)
                .subscribe(getCurrentSubscribeStatus(requestUserId, targetUserId))
                .build();
    }

    @Override
    @Transactional
    public SubscribeStatusResponseMapper subscribeButtonClick(final Long requestUserId,
                                                              final Long targetUserId) {
        final Boolean status = getCurrentSubscribeStatus(requestUserId, targetUserId);

        if (!status) {
            subscriberRepository.save(
                    Subscriber.builder()
                    .subscriberUserId(requestUserId)
                    .targetUserId(targetUserId)
                    .build());
            return SubscribeStatusResponseMapper
                    .builder()
                    .subscriberId(requestUserId)
                    .targetUserId(targetUserId)
                    .subscribe(true).build();
        }

        subscriberRepository.deleteById(
                subscriberRepository.getSubscriberMappingId(requestUserId, targetUserId)
        );

        return SubscribeStatusResponseMapper
                .builder()
                .subscriberId(requestUserId)
                .targetUserId(targetUserId)
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


    private Boolean getCurrentSubscribeStatus(final Long requestUserId, final Long targetUserId) {
        return subscriberRepository.existsBySubscriberUserIdAndAndTargetUserId(requestUserId, targetUserId);
    }
}
