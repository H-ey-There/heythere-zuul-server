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
public class UserListener {
    private final UserListenerService userListenerService;

    private static final String USER_UPDATE_TOPIC = "user-information-updated";
    private static final String USER_MODIFY_TOPIC = "user-information-modified";
    private static final String USER_DELETED = "user-deleted";

    @KafkaListener(topics = {USER_UPDATE_TOPIC})
    public void onUserUpdate(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        userListenerService.processUpdateUserEvent(consumerRecord);
    }

    @KafkaListener(topics = {USER_DELETED})
    public void onUserDelete(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
        userListenerService.processDeleteUserEvent(consumerRecord);
    }

    @KafkaListener(topics = {USER_MODIFY_TOPIC})
    public void onUserModify(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        log.info("ConsumerRecord : {} ", consumerRecord );
    }

}
