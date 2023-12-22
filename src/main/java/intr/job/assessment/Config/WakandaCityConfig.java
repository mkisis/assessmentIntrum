package intr.job.assessment.Config;

import intr.job.assessment.AbstractionLayer.CityInService;
import intr.job.assessment.AbstractionLayer.FilePoster;
import intr.job.assessment.AbstractionLayer.FileRequester;
import intr.job.assessment.AbstractionLayer.UploadScheduler;
import intr.job.assessment.Wakanda.CityInServiceWakandaImpl;
import intr.job.assessment.Wakanda.FilePosterWakandaImpl;
import intr.job.assessment.Wakanda.FileRequesterWakandaImpl;
import intr.job.assessment.Wakanda.UploadSchedulerWakandaImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WakandaCityConfig {

    @Bean
    public FileRequester fileRequesterWakandaImpl() {
        return new FileRequesterWakandaImpl();
    }

    @Bean
    public UploadScheduler uploadSchedulerWakandaImpl() {
        return new UploadSchedulerWakandaImpl();
    }

    @Bean
    public FilePoster filePosterWakandaImpl() {
        return new FilePosterWakandaImpl();
    }

    @Bean
    public CityInService cityInService() {
        return new CityInServiceWakandaImpl(fileRequesterWakandaImpl(), uploadSchedulerWakandaImpl(), filePosterWakandaImpl());
    }
}
