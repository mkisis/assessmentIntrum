package intr.job.assessment.Wakanda;

import intr.job.assessment.AbstractionLayer.UploadScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

public class UploadSchedulerWakandaImpl implements UploadScheduler {

    @Value("${wakanda_upload_cron_expression}")
    private String cronExpression;

    @Override
    public void scheduleUpload(String cronExpression, Runnable runnable) {
        ThreadPoolTaskScheduler wakandaTaskScheduler = new ThreadPoolTaskScheduler();
        wakandaTaskScheduler.setPoolSize(2);
        wakandaTaskScheduler.setThreadNamePrefix("Wakanda");
        wakandaTaskScheduler.initialize();
        wakandaTaskScheduler.schedule(runnable, new CronTrigger(cronExpression));
    }

    @Override
    public String getCronExpression() {
        return cronExpression;
    }
}
