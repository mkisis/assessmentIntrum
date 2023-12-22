package intr.job.assessment.AbstractionLayer;

public interface UploadScheduler {

    void scheduleUpload(String cronExpression, Runnable runnable);
    String getCronExpression();
}
