package com.sample.model;

import io.micronaut.security.authentication.UserDetails;

import java.util.Collection;
import java.util.Map;

public class CustomUserDetails extends UserDetails {

    Long id;

    public CustomUserDetails(String username, Collection<String> roles) {
        super(username, roles);
    }

    public CustomUserDetails(String username, Collection<String> roles, Map<String, Object> attributes) {
        super(username, roles, attributes);
    }

    public CustomUserDetails(String username, Collection<String> roles, Long id){
        super(username, roles);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
