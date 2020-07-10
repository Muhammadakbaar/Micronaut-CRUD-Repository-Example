package api.config;

import io.micronaut.context.annotation.Factory;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Singleton;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Factory
public class CryptoFactory {

    @Singleton
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Singleton
    public Scheduler s(){
        return Schedulers.from(Executors.newFixedThreadPool(10));
    }

}
