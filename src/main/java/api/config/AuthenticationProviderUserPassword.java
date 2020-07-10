package api.config;

import api.service.UserService;
import io.micronaut.security.authentication.*;
import org.reactivestreams.Publisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Inject
    UserService service;

    @Inject
    PasswordEncoder encoder;

    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return service.findByEmail((String)authenticationRequest.getIdentity()).map(u -> {
            if(encoder.matches(authenticationRequest.getSecret().toString(), u.getPassword())){
                Map<String, Object> extras = new HashMap<>();
                extras.put("user_id", u.getId());
                return new UserDetails(u.getUsername(), new ArrayList<>(), extras);
            }
            return new AuthenticationFailed();
        }).toFlowable();
    }
}
