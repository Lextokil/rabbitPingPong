package core.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {RabbitMqPropertyConfiguration.class})
public class EnableRabbitMqPropertyConfigure {
}
