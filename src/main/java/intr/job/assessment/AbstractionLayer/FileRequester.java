package intr.job.assessment.AbstractionLayer;

import org.springframework.scheduling.TaskScheduler;

import java.util.List;


public interface FileRequester {

    public <T> List<T> getListOfPayments(String localFilePath, Class<T> clazz);
}
