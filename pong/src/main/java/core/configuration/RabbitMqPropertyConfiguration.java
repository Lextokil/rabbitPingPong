package core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
    private String host;

    @NotNull
    @NotBlank
    private String user;

    @NotNull
    @NotBlank
    private String password;


}
