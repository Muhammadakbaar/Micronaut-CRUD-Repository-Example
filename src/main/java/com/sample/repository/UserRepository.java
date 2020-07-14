package com.sample.repository;

import com.sample.model.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
/*
* Nativamente, o Micronaut Data, fornce:
* CrudRepository(spring Like)
* GenericRepository(Igual ao Repository do Spring)
* RxJavaCrudRepository (compativel com QUALQUER banco(mongo, mysql, Redis, Cassandra) e lib de acesso a dados(jpa)
* AsyncCrudRepository(trabalha com CompletabeFutures, mesma compatibilidade do Rx)
* ReactiveStreamsCrudRepository(tambem compativel)
* */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
