package com.heythere.zuul.auth.controller;

import com.heythere.zuul.auth.mapper.SubscribeStatusResponseMapper;
import com.heythere.zuul.auth.mapper.UserResponseMapper;
import com.heythere.zuul.auth.security.AuthUser;
import com.heythere.zuul.auth.security.Authentication;
import com.heythere.zuul.auth.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("subscribe")
    public SubscribeStatusResponseMapper subscribeButtonClick(@Authentication final AuthUser authUser,
                                                              @RequestParam("targetId") final Long targetId) {
        return subscribeService.subscribeButtonClick(authUser.getId(), targetId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("subscribe/status")
    public SubscribeStatusResponseMapper subscribeStatus(@Authentication final AuthUser authUser,
                                                         @RequestParam("targetId") final Long targetId) {
        return subscribeService.subscribeStatus(authUser.getId(), targetId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("subscribers")
    public List<UserResponseMapper> retrieveAllMySubscriber(@Authentication final AuthUser authUser) {
        return subscribeService.retrieveAllMySubscriber(authUser.getId());
    }
}
