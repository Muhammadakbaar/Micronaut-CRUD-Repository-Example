package api.events.listeners;

import api.client.UserClient;
import api.events.UserCreatedEvent;
import io.micronaut.runtime.event.annotation.EventListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
public class UserCreatedEventListener {

    @Inject
    UserClient client;

    @EventListener
    public void userCreated(@NotNull UserCreatedEvent event) {
        client.send(event.getUser());
    }
}
