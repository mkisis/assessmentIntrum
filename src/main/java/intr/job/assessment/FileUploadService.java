package intr.job.assessment;

import intr.job.assessment.AbstractionLayer.CityInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileUploadService {
    @Autowired
    private CityInService wakandaCity;

    @EventListener(ApplicationReadyEvent.class)
    public void triggerProcesses() {
        wakandaCity.schedulePrepareAndExecute();
    }
}
