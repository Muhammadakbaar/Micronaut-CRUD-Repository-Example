package api.controller;

import api.model.User;
import api.service.UserService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.token.jwt.validator.AuthenticationJWTClaimsSetAdapter;
import io.reactivex.Single;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import java.security.Principal;

@Controller("/user")
@Secured("isAuthenticated()")
public class UserController {

    @Inject
    UserService service;

    @Get("/{id}")
    @Status(HttpStatus.OK)
    public Single<User> findById(@PathVariable Long id){
        return service.findById(id);
    }

    @Post
    @PermitAll
    @Status(HttpStatus.CREATED)
    public Single<User> save(@Body User user){
        return service.save(user);
    }

    @Put
    @Status(HttpStatus.OK)
    public Single<User> update(@Body User user){
        return service.update(user);
    }

    @Get("/authentication")
    @Status(HttpStatus.OK)
    public AuthenticationJWTClaimsSetAdapter getAuthenticationDetails(Principal principal){
        return (AuthenticationJWTClaimsSetAdapter) principal;
    }

}
