package com.heythere.zuul.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heythere.zuul.auth.dto.RegisterUserRequestUserDto;
import com.heythere.zuul.auth.mapper.UserResponseMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface UserService {
    Long save(final RegisterUserRequestUserDto payload) throws JsonProcessingException;
    UserResponseMapper findUserById(final Long requestUserId);
    UserResponseMapper updateImg(final Long requestUserId, final MultipartFile image) throws IOException, InterruptedException, ExecutionException, TimeoutException;
    void deleteUserById(final Long requestUserId) throws JsonProcessingException;
}
