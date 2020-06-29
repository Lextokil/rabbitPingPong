import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ComponentScan(basePackages = "core")
@EnableMongoRepositories(basePackages = "core")
@EnableJpaRepositories(basePackages = "core.dbh2")
@EntityScan(basePackages = "core.dbh2")
@SpringBootApplication
public class PongApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(PongApplication.class, args);

    }

}
