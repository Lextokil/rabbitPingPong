package core.job;

import core.decorator.CoreDBService;
import core.decorator.PongDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DbTransferJobService {

    private final CoreDBService coreDBService;

    @Autowired
    public DbTransferJobService(CoreDBService coreDBService) {
        this.coreDBService = coreDBService;
    }

    @Transactional
    public void transferAllDataFromH2ToMongo() {
        try {
            List<PongDTO> pongsFromH2 = coreDBService.h2FindAll();
            coreDBService.mongoSaveAll(pongsFromH2);
            log.info("--------------------------------------------------");
            log.info("Transfered: " + pongsFromH2.size() + " Pongs to mongo");
            log.info("--------------------------------------------------");
            coreDBService.h2DeleteAll();
        } catch (RuntimeException e) {
            log.info("An error ocurred in data transfer...");
            log.error(e.getStackTrace().toString());
        }
    }
}
