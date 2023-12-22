package intr.job.assessment.Config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClient() {
        return WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                    LoggerFactory.getLogger("FileRequester")
                            .info("Making request method: [{}] , URL: [{}]", clientRequest.method(), clientRequest.url());
                    return Mono.just(clientRequest);}))
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    LoggerFactory.getLogger("FileRequester")
                            .info("Getting response. Status code: {}, body {}, headers{}", clientResponse.statusCode(),
                                    clientResponse.bodyToMono(String.class), clientResponse.headers());
                    return Mono.just(clientResponse);}))
                .build();
    }
}
