package com.heythere.zuul.auth.message.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heythere.zuul.auth.mapper.UserResponseMapper;
import com.heythere.zuul.auth.message.domain.community.CommunityPostEventValue;
import com.heythere.zuul.auth.message.domain.community.CommunityPostUploadListenerEventDto;
import com.heythere.zuul.auth.message.domain.community.CommunityPostUploadSendEventDto;
import com.heythere.zuul.auth.message.domain.live.LiveStreamingEventValue;
import com.heythere.zuul.auth.message.domain.live.LiveStreamingListenerEventDto;
import com.heythere.zuul.auth.message.domain.live.LiveStreamingSendEventDto;
import com.heythere.zuul.auth.message.domain.video.VideoEventValue;
import com.heythere.zuul.auth.message.domain.video.VideoUploadSendEventDto;
import com.heythere.zuul.auth.message.domain.video.VideoUploadListenEventDto;
import com.heythere.zuul.auth.message.sender.UserEventProducer;
import com.heythere.zuul.auth.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageListenerService {
    private final ObjectMapper objectMapper;
    private final SubscribeService subscribeService;
    private final UserEventProducer userEventProducer;

    @Transactional
    public void sendVideoPostMail(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        final VideoUploadListenEventDto eventSnippet = objectMapper.readValue(consumerRecord.value(), VideoUploadListenEventDto.class);
        log.info("upload event publisher id : {} ", eventSnippet.getUserId());

        if(eventSnippet.getUserId()!=null && eventSnippet.getUserId()==000){
            throw new RecoverableDataAccessException("Temporary Network Issue");
        }

        final VideoUploadSendEventDto message =  VideoUploadSendEventDto.builder()
                .userId(eventSnippet.getUserId())
                .value(new VideoEventValue(eventSnippet.getSnippet(),
                       getSubscribers(eventSnippet.getUserId()))).build();

        userEventProducer.sendVideoUploadEvent(message);
    }

    @Transactional
    public void sendCommunityPostMail(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException{
        final CommunityPostUploadListenerEventDto eventSnippet = objectMapper.readValue(consumerRecord.value(), CommunityPostUploadListenerEventDto.class);
        log.info("upload event publisher id : {} ", eventSnippet.getUserId());

        if(eventSnippet.getUserId()!=null && eventSnippet.getUserId()==000){
            throw new RecoverableDataAccessException("Temporary Network Issue");
        }

        final CommunityPostUploadSendEventDto message = CommunityPostUploadSendEventDto.builder()
                .userId(eventSnippet.getUserId())
                .value(new CommunityPostEventValue(eventSnippet.getSnippet(), getSubscribers(eventSnippet.getUserId())))
                .build();
        userEventProducer.sendCommunityPostUploadEvent(message);
    }

    @Transactional
    public void sendLiveStreamingMail(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        final LiveStreamingListenerEventDto eventSnippet = objectMapper.readValue(consumerRecord.value(), LiveStreamingListenerEventDto.class);
        log.info("live streaming publisher id : {} ", eventSnippet.getUserId());

        if(eventSnippet.getUserId()!=null && eventSnippet.getUserId()==000){
            throw new RecoverableDataAccessException("Temporary Network Issue");
        }

        final LiveStreamingSendEventDto message = LiveStreamingSendEventDto.builder()
                .userId(eventSnippet.getUserId())
                .value(new LiveStreamingEventValue(eventSnippet.getSnippet(), getSubscribers(eventSnippet.getUserId())))
                .build();

        userEventProducer.sendLiveStreamingStartEvent(message);
    }

    private List<UserResponseMapper> getSubscribers(final Integer id) {
        return subscribeService.retrieveAllMySubscriber(id.longValue());
    }

}
