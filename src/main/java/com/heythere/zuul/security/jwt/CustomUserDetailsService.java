package com.heythere.zuul.security.jwt;

import com.heythere.zuul.user.repository.UserRepository;
import com.heythere.zuul.security.AuthUser;
import com.heythere.zuul.security.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return AuthUser.create(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email))
        );

    }

    @Transactional
    public UserDetails loadUserById(final Long id) {
        return AuthUser.create(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id))
        );
    }
}
