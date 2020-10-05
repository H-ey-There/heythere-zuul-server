package com.heythere.zuul.user.service;

import com.heythere.zuul.security.exception.ResourceNotFoundException;
import com.heythere.zuul.security.payload.CurrentUser;
import com.heythere.zuul.user.model.AuthProvider;
import com.heythere.zuul.user.model.User;
import com.heythere.zuul.user.repository.UserRepository;
import com.heythere.zuul.security.exception.BadRequestException;
import com.heythere.zuul.security.payload.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(final SignUpRequest payload) {
        if (userRepository.existsByEmail(payload.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        return userRepository.save(
                User.builder()
                        .name(payload.getName())
                        .email(payload.getEmail())
                        .password(passwordEncoder.encode(payload.getPassword()))
                        .provider(AuthProvider.LOCAL).build());
    }


    @Transactional
    public CurrentUser getCurrentUser(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));

        return CurrentUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImageUrl())
                .build();
    }
}
