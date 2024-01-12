package cicuitbreaker.springretry.client;

import cicuitbreaker.springretry.common.exception.RemoteServiceNotAvailableException;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BackendAdapterImpl implements BackendAdapter {

    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public String getBackendResponse() {

        int random1 = new Random().nextInt(4);
        int random2 = new Random().nextInt(4);

        log.info("getBackendResponse random1 = %d, random2 = %d".formatted(random1, random2));

        var builder = UriComponentsBuilder.fromUriString("http://localhost:3000/sum")
                .queryParam("param1", random1)
                .queryParam("param2", random2);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);
            return response.getBody();
        } catch (Exception e) {
            log.log(Level.FINE, e.getMessage());
            throw new RemoteServiceNotAvailableException(e.getMessage());
        }
    }

    @Override
    public String getBackendResponseFallback(RemoteServiceNotAvailableException e) {
        return "Hello from fallback method!!! " + e.getMessage();
    }
}