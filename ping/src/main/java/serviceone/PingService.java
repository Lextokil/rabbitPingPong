package serviceone;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
public class PingService {

    public static final String EXCHANGE_NAME = "ping";
    public static final String QUEUE_NAME = "ping";
    public static Integer round = 0;

    private Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("rabbitmq");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
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
        channel.basicPublish(EXCHANGE_NAME, "ping", props.build(), message.getBytes("UTF-8"));
        log.info("Sended: " + message);
        round++;
    }

    private Channel createEchangesAndQueue(Channel channel) throws IOException {
        Map<String, Object> pongArgs = new HashMap<>();
        pongArgs.put("x-delayed-type", "direct");
        channel.exchangeDeclare(EXCHANGE_NAME, "x-delayed-message", true, false, pongArgs);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "ping");
        return channel;
    }

    public void createRabbitManager() throws IOException, TimeoutException {
        String queueName = "pong";
        callBack(createChannel(createConnection()), queueName);
    }


}
