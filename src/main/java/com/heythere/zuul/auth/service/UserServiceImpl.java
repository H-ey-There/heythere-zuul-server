package com.heythere.zuul.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heythere.zuul.auth.dto.RegisterUserRequestUserDto;
import com.heythere.zuul.auth.mapper.UserResponseMapper;
import com.heythere.zuul.auth.model.User;
import com.heythere.zuul.auth.repository.UserRepository;
import com.heythere.zuul.auth.security.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long save(RegisterUserRequestUserDto payload) {
        return userRepository.save(User.builder()
                .email(payload.getEmail())
                .name(payload.getName())
                .password(passwordEncoder.encode(payload.getPassword()))
                .build()).getId();
    }

    @Override
    @Transactional
    public UserResponseMapper findUserById(Long requestUserId) {
        return UserResponseMapper.of(
                userRepository.findById(requestUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestUserId)));
    }

    @Override
    public UserResponseMapper updateImg(Long requestUserId, MultipartFile image) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    @Transactional
    public void deleteUserById(Long requestUserId) throws JsonProcessingException {
        userRepository.deleteById(requestUserId);
    }
}
