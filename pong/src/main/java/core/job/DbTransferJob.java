package core.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DisallowConcurrentExecution
public class DbTransferJob implements Job {

    private final DbTransferJobService dbTransferJobService;

    public DbTransferJob(DbTransferJobService dbTransferJobService) {
        this.dbTransferJobService = dbTransferJobService;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("***********************");
        log.info("*~* JOB IS WORKING *~*");
        log.info("***********************");
        dbTransferJobService.transferAllDataFromH2ToMongo();
    }
}
