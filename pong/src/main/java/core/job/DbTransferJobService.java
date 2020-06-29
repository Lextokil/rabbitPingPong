package core.job;

import core.decorator.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DbTransferJobService {


    private final DbH2Repository dbH2Repository;

    private final CoreDBService coreDBService;

    public DbTransferJobService(DbH2Repository dbH2Repository, CoreDBService coreDBService) {
        this.dbH2Repository = dbH2Repository;
        this.coreDBService = coreDBService;
    }


    @Transactional
    public void transferAllDataFromH2ToMongo() {
        try {
            List<PongDTO> pongsFromH2 = dbH2Repository.findAll();
            log.info("--------------------------------------------------");
            log.info("Transfered: " + pongsFromH2.size() + " Pongs to mongo");
            log.info("--------------------------------------------------");
            dbH2Repository.deleteAll();
            coreDBService.SaveAll(pongsFromH2);
        } catch (RuntimeException e) {
            log.info("An error ocurred in data transfer...");
            log.error(e.getStackTrace().toString());
        }
    }
}
