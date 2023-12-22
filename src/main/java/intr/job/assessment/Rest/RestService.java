package intr.job.assessment.Rest;

import intr.job.assessment.AbstractionLayer.CityInService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class RestService {

    CityInService wakandaCity;


    @PostMapping("/initupload")
    public void initUploadProcess() {
        wakandaCity.prepareRequestsAndExecute();
    }
}
