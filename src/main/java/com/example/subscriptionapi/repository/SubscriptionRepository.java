package com.example.subscriptionapi.repository;

import com.example.subscriptionapi.data.entity.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryDefinition(idClass = UUID.class, domainClass = Subscription.class)
public interface SubscriptionRepository {

    Optional<Subscription> findById(UUID id);

    @Query("""
            SELECT s
            FROM Subscription s
            LEFT JOIN s.users u
            GROUP BY s
            ORDER BY COUNT(u) DESC
            """)
    List<Subscription> findTopSubscriptions(Pageable pageable);

    Subscription save(Subscription subscription);

    void deleteById(UUID id);
}
