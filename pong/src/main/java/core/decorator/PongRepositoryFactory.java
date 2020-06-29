package core.decorator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class PongRepositoryFactory {

    @Bean
    @Primary
    public IPongRepository build(DbMongoRepository dbMongoRepository, DbH2Repository dbH2Repository) {
        final List<IPongRepository> iPongRepositories = Arrays.asList(dbMongoRepository, dbH2Repository);

        return new ProxyPongRepository(iPongRepositories);
    }
}
