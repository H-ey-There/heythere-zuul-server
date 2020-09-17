package com.heythere.zuul.user.repository;

import com.heythere.zuul.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);
    Optional<User> findById(final Long id);
    Boolean existsByEmail(final String email);
}
