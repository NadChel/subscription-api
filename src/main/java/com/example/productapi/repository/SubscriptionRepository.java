package com.example.productapi.repository;

import com.example.productapi.data.entity.Subscription;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.UUID;

@RepositoryDefinition(idClass = UUID.class, domainClass = Subscription.class)
public interface SubscriptionRepository {

    Subscription save(Subscription subscription);

    void deleteById(UUID id);
}
