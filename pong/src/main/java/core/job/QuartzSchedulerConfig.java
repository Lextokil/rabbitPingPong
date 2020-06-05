package core.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzSchedulerConfig {

    private final DbTransferProperty dbTransferProperty;

    public QuartzSchedulerConfig(DbTransferProperty dbTransferProperty) {
        this.dbTransferProperty = dbTransferProperty;
    }

    @Bean
    public JobDetail createDbTransferJob() {
        return this.createJob("DbTransfer", "Transfer all data from H2 to Mongodb", DbTransferJob.class);
    }

    @Bean
    public Trigger setTriggerToJob(JobDetail jobDetail) {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(dbTransferProperty.getCronseconds())
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("DbTransferTrigger")
                .withSchedule(simpleScheduleBuilder)
                .startNow()
                .build();
    }

    private JobDetail createJob(String jobName, String jobDescription, Class classType) {
        return JobBuilder
                .newJob(classType)
                .withIdentity(jobName)
                .usingJobData("teste", "teste")
                .withDescription(jobDescription)
                .storeDurably(true)
                .requestRecovery(true)
                .build();
    }
}
