package com.heythere.zuul.auth.message.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heythere.zuul.auth.message.domain.UserEventDto;
import com.heythere.zuul.auth.message.domain.UserMessageDto;
import com.heythere.zuul.auth.model.AuthProvider;
import com.heythere.zuul.auth.model.User;
import com.heythere.zuul.auth.repository.UserRepository;
import com.heythere.zuul.auth.security.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserListenerService {
    private final KafkaTemplate<Integer,String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Transactional
    public void processUpdateUserEvent(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        final UserEventDto userEvent = objectMapper.readValue(consumerRecord.value(), UserEventDto.class);
        log.info("User  : {} ", userEvent);
        if(userEvent.getUserEventId()!=null && userEvent.getUserEventId()==000){
            throw new RecoverableDataAccessException("Temporary Network Issue");
        }

        if (!userRepository.existsById(userEvent.getUserEventId().longValue())) {
            final User newUser = save(userEvent);
            log.info("User saved : {} ", newUser);
        } else {
            final User updateUser = userRepository.findById(userEvent.getUserEventId().longValue())
                    .orElseThrow(() -> new ResourceNotFoundException("User","id", userEvent.getUserEventId().longValue()))
                    .update(userEvent);
            log.info("User updated: {} ", updateUser);
        }

    }

    @Transactional
    public void processDeleteUserEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        final UserEventDto userEvent = objectMapper.readValue(consumerRecord.value(), UserEventDto.class);
        userRepository.deleteById(userEvent.getUserEventId().longValue());
        log.info("User deleted ~ ");
    }

    private User save(final UserEventDto userEvent) {
        final UserMessageDto userMessage = userEvent.getUserMessageDto();

        return userRepository.save(User.builder()
                .id(userEvent.getUserEventId().longValue())
                .email(userMessage.getEmail())
                .name(userMessage.getName())
                .password(userMessage.getPassword())
                .imageUrl(userMessage.getImg())
                .provider(AuthProvider.LOCAL)
                .build());
    }

    public void handleRecovery(final ConsumerRecord<Integer,String> record){

        final Integer key = record.key();
        final String message = record.value();

        final ListenableFuture<SendResult<Integer,String>> listenableFuture = kafkaTemplate.sendDefault(key, message);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, message, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, message, result);
            }
        });
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", key, value, result.getRecordMetadata().partition());
    }
}
