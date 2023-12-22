package intr.job.assessment.AbstractionLayer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class CityInService {
    private FileRequester fileRequester;
    private UploadScheduler uploadScheduler;
    private FilePoster filePoster;

    public void schedulePrepareAndExecute() {}
    public void prepareRequestsAndExecute() {}
}
