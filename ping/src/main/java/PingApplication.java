import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import serviceone.PingService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@SpringBootApplication
@ComponentScan(value = "serviceone")
public class PingApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(PingApplication.class, args);
        log.info("Ping Started! ");
        PingService ping = new PingService();
        ping.createRabbitManager();
    }

}
