package com.pedro.config;

import com.pedro.model.CustomUserDetails;
import com.pedro.model.User;
import com.pedro.repository.UserRepository;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Singleton // <1>
public class AuthenticationProviderUserPassword implements AuthenticationProvider { // <2>

    @Inject
    UserRepository repo;

    @Inject
    BCryptPasswordEncoder encoder;

    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        Optional<User> optionalUser = repo.findByEmail(authenticationRequest.getIdentity().toString());
        System.out.println(authenticationRequest.getSecret().toString() + authenticationRequest.getIdentity().toString());
        if (optionalUser.isEmpty()) {
            System.out.println("empty");
            return Flowable.just(new AuthenticationFailed());
        } else {
            User user = optionalUser.get();
            if (encoder.matches(authenticationRequest.getSecret().toString(), user.getPassword())) {
                System.out.println("passou");
                /*eh possivel adicionar claims(JWT) em um Map, que eh recebido em um dos construtores de UserDetail
                * OU
                * Criar uma classe que extende UserDetails e possui os atributos extras, porem, os mesmos deverao
                * ser mapeados em uma classe que extende JWTClaimsSetGenerator(Mais elegante)
                * */
                Map<String, Object> attributes = new HashMap<>();
                return Flowable.just(new CustomUserDetails(user.getEmail(), List.of("ADMIN"), user.getId()));
            } else {
                return Flowable.just(new AuthenticationFailed());
            }
        }
    }
}
