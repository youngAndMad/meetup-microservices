package cicuitbreaker.cicruitbreaker.service.impl;

import cicuitbreaker.cicruitbreaker.service.ResourceServer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;

@Service
public class ResourceServerImpl implements ResourceServer {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @CircuitBreaker(name = "someService" , fallbackMethod = "someServiceFallback")
    public String getResponseFromBackend() {
        int random1 = new Random().nextInt(4);
        int random2 = new Random().nextInt(4);

        System.out.printf("getBackendResponse random1 = %d, random2 = %d%n", random1, random2);

        var builder = UriComponentsBuilder.fromUriString("http://localhost:3000/not-sum")
                .queryParam("param1", random1)
                .queryParam("param2", random2);
        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        return response.toString();
    }

    @Retry(name="someService" , fallbackMethod = "retryFallback")
    public String retry(){
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:3000/not-found", String.class);
        return response.toString();
    }

    private String retryFallback(Exception e){
        return "err " + e.getMessage();
    }

    private String someServiceFallback(Throwable throwable){
        return "someServiceFallback by circuit breaker, appeared error by Beibit = " + throwable.getMessage();
    }
}
