package intr.job.assessment.Wakanda;

import com.google.gson.Gson;
import intr.job.assessment.AbstractionLayer.CityInService;
import intr.job.assessment.AbstractionLayer.FilePoster;
import intr.job.assessment.AbstractionLayer.FileRequester;
import intr.job.assessment.AbstractionLayer.UploadScheduler;
import intr.job.assessment.Wakanda.Models.PaymentInfo;
import intr.job.assessment.Wakanda.Models.PayoutRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter
@Setter
@Slf4j
public class CityInServiceWakandaImpl extends CityInService {
    @Value("${wakanda_local_file_path_wakanda}")
    private String localFilePath;

    @Value("${wakanda_post_url}")
    private String postUrl;

    public CityInServiceWakandaImpl(FileRequester fileRequester, UploadScheduler uploadScheduler, FilePoster filePoster) {
        super(fileRequester, uploadScheduler, filePoster);
    }

    @Override
    public void prepareRequestsAndExecute() {
        log.info("Checking for WAKANDA payout csv list in file path: " + localFilePath);
        List<PaymentInfo> paymentInfos = getFileRequester().getListOfPayments(localFilePath, PaymentInfo.class);
        if (paymentInfos == null)
            return;

        log.info("WAKANDA payment list size: " + paymentInfos.size());
        for (PaymentInfo paymentInfo : paymentInfos) {
            try {
                PayoutRequest payoutRequest = new PayoutRequest();
                payoutRequest.setPaymentDate(paymentInfo.getPaymentDate());
                payoutRequest.setPaymentAmount(Double.parseDouble(paymentInfo.getAmount().replace(",", ".")));
                payoutRequest.setCompanyIdentityNumber(paymentInfo.getCompanyTaxNumber());
                String jsonRequest = new Gson().toJson(payoutRequest);
                log.info("Sending WAKANDA payment info: " + jsonRequest);
                getFilePoster().sendPaymentInfo(payoutRequest, postUrl);
            } catch (Exception e) {
                log.error("Error while sending WAKANDA payment info: " + paymentInfo + " error: " + e.getMessage());
            }
        }
    }

    @Override
    public void schedulePrepareAndExecute() {
        try {
            getUploadScheduler().scheduleUpload(getUploadScheduler().getCronExpression(), this::prepareRequestsAndExecute);
        } catch (IllegalArgumentException e) {
            log.error("Invalid cron expression: " + getUploadScheduler().getCronExpression());
        }
    }
}
