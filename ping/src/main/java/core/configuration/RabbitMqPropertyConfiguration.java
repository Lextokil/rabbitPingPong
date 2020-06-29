package core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "hbsis.rabbit")
@Validated
@Getter
@Setter
public class RabbitMqPropertyConfiguration {

    @NotNull
    @NotBlank
    public String host;
    @NotNull
    @NotBlank
    public String user;

    @NotNull
    @NotBlank
    public String password;


}
