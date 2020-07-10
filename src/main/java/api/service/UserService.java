package api.service;

import api.model.User;
import io.reactivex.Single;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public interface UserService {

    Single<User> save(@Valid User user);

    Single<User> findById(@NotEmpty Long id);

    Single<User> update(@Valid User user);

    void deleteById(@NotEmpty Long id);

    Single<User> findByEmail(@NotEmpty String email);
}
