package core.serviceone;

import com.rabbitmq.client.*;
import core.configuration.RabbitMqPropertyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class PingService {

    public static final String EXCHANGE_NAME_PING = "ping";
    public static final String QUEUE_NAME_PING = "ping";
    public static final String EXCHANGE_NAME_PONG = "pong";
    public static final String QUEUE_NAME_PONG = "pong";
    public static Integer round = 0;
    private final RabbitMqPropertyConfiguration rabbitMqPropertyConfiguration;

    public PingService(RabbitMqPropertyConfiguration rabbitMqPropertyConfiguration) {
        this.rabbitMqPropertyConfiguration = rabbitMqPropertyConfiguration;
    }

    private Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitMqPropertyConfiguration.getHost());
        connectionFactory.setUsername(rabbitMqPropertyConfiguration.getUser());
        connectionFactory.setPassword(rabbitMqPropertyConfiguration.getPassword());
        return connectionFactory.newConnection();
    }

    private Channel createChannel(Connection connection) throws IOException {
        return createEchangesAndQueue(connection.createChannel());
    }

    private void callBack(Channel channel, String queueName) throws IOException {
        postPinginARow(channel);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            log.info("Received:" + message);
            postPinginARow(channel);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
        channel.basicQos(1);
    }

    private void postPinginARow(Channel channel) throws IOException {
        String message = "PING " + round;
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-delay", 10000);
        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers);
        channel.basicPublish(EXCHANGE_NAME_PING, "ping", props.build(), message.getBytes("UTF-8"));
        log.info("Sended: " + message);
        round++;
    }

    private Channel createEchangesAndQueue(Channel channel) throws IOException {
        Map<String, Object> pongArgs = new HashMap<>();
        pongArgs.put("x-delayed-type", "direct");
        channel.exchangeDeclare(EXCHANGE_NAME_PING, "x-delayed-message", true, false, pongArgs);
        channel.queueDeclare(QUEUE_NAME_PING, true, false, false, null);
        channel.queueBind(QUEUE_NAME_PING, EXCHANGE_NAME_PING, "ping");

        channel.exchangeDeclare(EXCHANGE_NAME_PONG, "direct");
        channel.queueDeclare(QUEUE_NAME_PONG, true, false, false, null);
        channel.queueBind(QUEUE_NAME_PONG, EXCHANGE_NAME_PONG, "pong");
        return channel;
    }

    @PostConstruct
    public void createRabbitManager() throws IOException, TimeoutException {
        String queueName = "pong";
        callBack(createChannel(createConnection()), queueName);
    }


}
