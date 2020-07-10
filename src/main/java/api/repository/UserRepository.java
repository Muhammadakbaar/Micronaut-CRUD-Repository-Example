package api.repository;

import api.model.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Maybe;

@Repository
public interface UserRepository extends RxJavaCrudRepository<User, Long> {

    Maybe<User> findByEmail(String email);

}
