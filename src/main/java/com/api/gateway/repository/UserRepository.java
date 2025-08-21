package com.api.gateway.repository;

import com.api.gateway.model.entity.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    @Query("{'email': ?0}")
    Mono<User> findByEmail(String email);

    @Query("{'email': ?0, 'active': ?1}")
    Mono<User> findByEmailAndActive(String email, boolean active);
}

