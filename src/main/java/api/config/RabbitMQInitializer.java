package api.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.configuration.rabbitmq.connect.ChannelInitializer;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class RabbitMQInitializer extends ChannelInitializer {

    @Override
    public void initialize(Channel channel) throws IOException {
        channel.exchangeDeclare("micronaut", BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare("users", true, false, false, null);
        channel.queueBind("users", "micronaut", "users");
    }
}
