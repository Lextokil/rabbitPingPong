package core.job;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ConfigurationProperties(prefix = "hbsis.dbtransfer")
@Validated
@Component
public class DbTransferProperty {

    @NotNull
    private int cronseconds;
}
