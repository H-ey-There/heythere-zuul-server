package com.heythere.zuul.auth.message.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heythere.zuul.auth.message.domain.MailEventDto;
import com.heythere.zuul.auth.message.domain.UserEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserEventProducer {
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String USER_UPDATE_TOPIC = "user-information-updated";
    private static final String USER_DELETED = "user-deleted";
    private static final String SEND_WELCOME_MAIL = "send-welcome-mail";

    public void sendUserUpdateEvent(final UserEventDto userMessage) throws JsonProcessingException {
        final Integer key = userMessage.getUserEventId();
        final String value = objectMapper.writeValueAsString(userMessage);

        final ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, USER_UPDATE_TOPIC);

        final ListenableFuture<SendResult<Integer, String>> listenableFuture
                // = kafkaTemplate.send(topic,key, value);
                = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(final Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(final SendResult<Integer,String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    public void sendUserDeletedEvent(final UserEventDto userMessage) throws JsonProcessingException {
        final Integer key = userMessage.getUserEventId();
        final String value = objectMapper.writeValueAsString(userMessage);

        final ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, USER_DELETED);

        final ListenableFuture<SendResult<Integer, String>> listenableFuture
                // = kafkaTemplate.send(topic,key, value);
                = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(final Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(final SendResult<Integer,String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    public void sendWelcomeMailEvent(final MailEventDto mailEvent) throws JsonProcessingException {
        final Integer key = mailEvent.getUserId();
        final String value = objectMapper.writeValueAsString(mailEvent);

        final ProducerRecord<Integer, String> producerRecord = buildProducerRecord(key, value, SEND_WELCOME_MAIL);

        final ListenableFuture<SendResult<Integer, String>> listenableFuture
                = kafkaTemplate.send(producerRecord);
        listenableFuture.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(final Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(final SendResult<Integer,String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    private ProducerRecord<Integer, String> buildProducerRecord(final Integer key, final String value, final String topic) {
        // Header
        final List<Header> headers = List.of(new RecordHeader("event-source", "scanner".getBytes()));
        return new ProducerRecord<>(topic, null, key, value, headers);
    }

    public SendResult<Integer,String> sendLibraryEventSynchronous(final UserEventDto event) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        final Integer key = event.getUserEventId();
        final String value = objectMapper.writeValueAsString(event);
        SendResult<Integer,String> sendResult = null;

        try {
            sendResult =  kafkaTemplate.sendDefault(key, value).get(2, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            log.error("ExecutionException, InterruptedException Sending the Message and the exception is {}", e);
            throw e;
        } catch (Exception e) {
            log.error("Exception Sending the Message and the exception is {}", e);
            throw e;
        }
        return sendResult;
    }

    private void handleFailure(final Integer key, final String value, final Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex);
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure : {}", throwable.getMessage());
        }
    }

    private void handleSuccess(final Integer key, final String value, final SendResult<Integer, String> result) {
        log.info("Message Send Successfully! key : {}, value : {}, partition : {}", key, value, result.getRecordMetadata().partition());
    }
}
