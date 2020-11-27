package com.heythere.zuul.auth.message.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageListener {
    private static final String VIDEO_UPLOAD = "video-upload";
    private static final String COMMUNITY_POST_UPLOAD = "community_post_upload";
    private static final String LIVE_STREAMING_START = "live_streaming_start";

    private final MessageListenerService messageListenerService;

    @KafkaListener(topics = {VIDEO_UPLOAD})
    public void videoUploadEventListener(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        messageListenerService.sendVideoPostMail(consumerRecord);
    }

    @KafkaListener(topics = {COMMUNITY_POST_UPLOAD})
    public void setCommunityPostUploadEventListener(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        messageListenerService.sendCommunityPostMail(consumerRecord);
    }

    @KafkaListener(topics = {LIVE_STREAMING_START})
    public void setLiveStreamingStartEventListener(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        messageListenerService.sendLiveStreamingMail(consumerRecord);
    }
}
