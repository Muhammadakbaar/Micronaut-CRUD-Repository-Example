package api.service.impl;

import api.events.UserCreatedEvent;
import api.model.User;
import api.repository.UserRepository;
import api.service.UserService;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Singleton
@Slf4j
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncoder encoder;

    @Inject
    ApplicationEventPublisher eventPublisher;

    @Inject
    Scheduler ioScheduler;

    @Override
    public Single<User> save(@Valid User user) {
         return Single.just(user).map(u -> {
            u.setPassword(encoder.encode(u.getPassword()));
            return u;
         }).flatMap(userRepository::save).subscribeOn(ioScheduler)
                 .doOnSuccess(u -> log.info("Persisted user: " + u))
                 .doAfterSuccess(u -> eventPublisher.publishEvent(new UserCreatedEvent(u)));
    }

    @Override
    public Single<User> findById(@NotEmpty Long id) {
        return userRepository.findById(id).switchIfEmpty(Single.error(RuntimeException::new));
    }

    @Override
    public Single<User> update(@Valid User user) {
        return Single.just(user).flatMap(u -> u.getId() == null ?
                Single.error(new RuntimeException()):
                userRepository.save(u));
    }

    @Override
    public void deleteById(@NotEmpty Long id) {
        userRepository.deleteById(id)
                .doOnComplete(() -> log.info("Deleted user with id:" + id));
    }

    @Override
    @Transactional(Transactional.TxType.NEVER)
    public Single<User> findByEmail(@NotEmpty String email) {
        return userRepository.findByEmail(email).switchIfEmpty(Single.error(RuntimeException::new));
    }
}
