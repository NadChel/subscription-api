package com.example.productapi.repository;

import com.example.productapi.data.entity.User;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;
import java.util.UUID;

@RepositoryDefinition(idClass = UUID.class, domainClass = User.class)
public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    void deleteById(UUID id);
}
