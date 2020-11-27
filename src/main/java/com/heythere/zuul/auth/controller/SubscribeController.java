package com.heythere.zuul.auth.controller;

import com.heythere.zuul.auth.dto.SubscribeRequestDto;
import com.heythere.zuul.auth.mapper.SubscribeStatusResponseMapper;
import com.heythere.zuul.auth.mapper.UserResponseMapper;
import com.heythere.zuul.auth.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping("subscribe")
    public SubscribeStatusResponseMapper subscribeButtonClick(final SubscribeRequestDto payload) {
        return subscribeService.subscribeButtonClick(payload);
    }

    @PostMapping("subscribe/status")
    public SubscribeStatusResponseMapper subscribeStatus(final SubscribeRequestDto payload) {
        return subscribeService.subscribeStatus(payload);
    }

    @GetMapping("me/{id}/subscribers")
    public List<UserResponseMapper> retrieveAllMySubscriber(@PathVariable("id") final Long id) {
        return subscribeService.retrieveAllMySubscriber(id);
    }


}
