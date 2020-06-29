import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "core")
public class PingApplication {

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(PingApplication.class, args);
    }

}
