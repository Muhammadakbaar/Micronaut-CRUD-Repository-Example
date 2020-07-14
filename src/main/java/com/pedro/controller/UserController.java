package com.pedro.controller;

import com.pedro.config.BCryptPasswordEncoder;
import com.pedro.model.User;
import com.pedro.repository.UserRepository;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.token.jwt.validator.AuthenticationJWTClaimsSetAdapter;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.security.Principal;

/**
 * Endpoints podem ser protegidos com a Annotation Secured, ou
 * @RolesAllowed
 * @PermitAll
 * @DenyAll
 */


@Controller("/user")
@Secured("isAuthenticated()")
public class UserController {

    @Inject
    UserRepository repository;

    @Inject
    BCryptPasswordEncoder encoder;

    @Get
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Get("{id}")
    @RolesAllowed("ADMIN")
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Post
    @PermitAll
    public User create(@Body User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Put
    public User update(User u){
        return repository.update(u);
    }

    @Get("/principal")
    public AuthenticationJWTClaimsSetAdapter principal(Principal principal){
        return (AuthenticationJWTClaimsSetAdapter) principal;
    }


}
