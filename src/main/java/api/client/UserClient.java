package api.client;

import api.model.User;
import io.micronaut.configuration.rabbitmq.annotation.Binding;
import io.micronaut.configuration.rabbitmq.annotation.RabbitClient;

@RabbitClient("micronaut")
public interface UserClient {

    @Binding("users")
    void send(User user);

}
