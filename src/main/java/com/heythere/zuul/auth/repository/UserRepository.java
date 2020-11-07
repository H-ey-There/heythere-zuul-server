package com.heythere.zuul.auth.repository;

import com.heythere.zuul.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);
    Optional<User> findById(final Long id);
    Boolean existsByEmail(final String email);
}
