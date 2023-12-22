package intr.job.assessment.Wakanda;

import intr.job.assessment.AbstractionLayer.FilePoster;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class FilePosterWakandaImpl implements FilePoster {

    @Autowired
    private WebClient webClient;

    @Override
    public void sendPaymentInfo(Object payoutRequest, String url) {
        webClient.post().uri(url)
                .bodyValue(payoutRequest)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(2)))
                .doOnRequest(req -> log.info("Sending payment info to Intrum request:" + payoutRequest))
                .doOnError(err -> log.error("Error sending payment info to Intrum resp message:" + err.getMessage()))
                .doOnSuccess(successResp -> log.info("Successfully sent payment info to Intrum response:" + successResp))
                .subscribe();
    }
}
