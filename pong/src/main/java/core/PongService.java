package core;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import core.configuration.RabbitMqPropertyConfiguration;
import core.decorator.CoreDBService;
import core.decorator.PongDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class PongService {

    private final CoreDBService coreDBService;
    public static final String EXCHANGE_NAME_PING = "ping";
    public static final String QUEUE_NAME_PING = "ping";
    public static final String EXCHANGE_NAME_PONG = "pong";
    public static final String QUEUE_NAME_PONG = "pong";
    private final RabbitMqPropertyConfiguration rabbitMqPropertyConfiguration;

    public PongService(CoreDBService coreDBService, RabbitMqPropertyConfiguration rabbitMqPropertyConfiguration) {
        this.coreDBService = coreDBService;
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
        return createExchangeAndQueue(connection.createChannel());
    }

    private void callback(Channel channel, String queueName) throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            PongDTO pongDTO = new PongDTO();
            pongDTO.setMsg(createPongMessage(message));
            log.info("Received: " + message);
            message = coreDBService.SaveOrUpdate(pongDTO).getMsg();
            returnPong(channel, message);


        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
        channel.basicQos(1);
    }

    private Channel createExchangeAndQueue(Channel channel) throws IOException {
        channel.exchangeDeclare(EXCHANGE_NAME_PONG, "direct");
        channel.queueDeclare(QUEUE_NAME_PONG, true, false, false, null);
        channel.queueBind(EXCHANGE_NAME_PONG, EXCHANGE_NAME_PONG, "pong");

        Map<String, Object> pongArgs = new HashMap<>();
        pongArgs.put("x-delayed-type", "direct");
        channel.exchangeDeclare(EXCHANGE_NAME_PING, "x-delayed-message", true, false, pongArgs);
        channel.queueDeclare(QUEUE_NAME_PING, true, false, false, null);
        channel.queueBind(QUEUE_NAME_PING, EXCHANGE_NAME_PING, "ping");
        return channel;
    }

    private void returnPong(Channel channel, String message) throws IOException {
        createExchangeAndQueue(channel);
        channel.basicPublish(EXCHANGE_NAME_PONG, "pong", null, message.getBytes("UTF-8"));
        log.info("Sended: " + message);
    }

    private String createPongMessage(String pingMessage) {
        pingMessage = pingMessage.replaceAll("\\D+", "");
        return "PONG " + pingMessage;
    }

    @PostConstruct
    public void createRabbitManager() throws IOException, TimeoutException {
        String queueName = "ping";
        callback(createChannel(createConnection()), queueName);
    }


}
