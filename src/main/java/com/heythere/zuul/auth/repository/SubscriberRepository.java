package com.heythere.zuul.auth.repository;

import com.heythere.zuul.auth.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    boolean existsBySubscriberUserIdAndAndTargetUserId(final Long subscriberId, final Long targetUserId);

    @Query("select s.id from Subscriber s where s.subscriberUserId = ?1 and s.targetUserId = ?2")
    Long getSubscriberMappingId(final Long subscriberId, final Long targetUserId );

    List<Subscriber> findAllByTargetUserId(final Long id);
}
