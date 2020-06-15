package core;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import core.decorator.CoreDBService;
import core.decorator.PongDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class PongService {

    @Autowired
    private CoreDBService coreDBService;

    public static final String EXCHANGE_NAME = "pong";
    public static final String QUEUE_NAME = "pong";


    private Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("rabbitmq");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory.newConnection();
    }

    private Channel createChannel(Connection connection) throws IOException {
        return createExchangeAndQueue(connection.createChannel());
    }

    private void callback(Channel channel, String queueName) throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            try {
                if (Math.random() <= 0.3) {
                    log.error(message + " ...CORONA INFECTED YOUR MONGODB SAVING IN H2 ");
                    throw new RuntimeException("CABUM");
                }
                PongDTO pongDTO = new PongDTO();
                pongDTO.setMsg(createPongMessage(message));
                coreDBService.mongoSaveOrUpdate(pongDTO);
                log.info("Received: " + message);
                returnPong(channel, createPongMessage(message));


            } catch (Exception e) {
                PongDTO p = new PongDTO();
                p.setMsg(createPongMessage(message));
                coreDBService.h2SaveOrUpdate(p);
                returnPong(channel, p.getMsg());
            }


        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
        channel.basicQos(1);
    }

    private Channel createExchangeAndQueue(Channel channel) throws IOException {
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "pong");
        return channel;
    }

    private void returnPong(Channel channel, String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, "pong", null, message.getBytes("UTF-8"));
        log.info("Sended: " + message);

    }

    private String createPongMessage(String pingMessage) {
        pingMessage = pingMessage.replaceAll("\\D+", "");
        return "PONG " + pingMessage;
    }

    public void createRabbitManager() throws IOException, TimeoutException {
        String queueName = "ping";
        callback(createChannel(createConnection()), queueName);
    }


}
