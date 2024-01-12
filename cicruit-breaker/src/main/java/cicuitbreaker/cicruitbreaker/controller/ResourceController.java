package cicuitbreaker.cicruitbreaker.controller;

import cicuitbreaker.cicruitbreaker.service.ResourceServer;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/circuit-breaker/")
public class ResourceController {

    private final ResourceServer resourceServer;

    public ResourceController(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    @GetMapping
    ResponseEntity<?> getSomeServiceResponse(){
        return ResponseEntity
                .ok(Map.of("response",resourceServer.getResponseFromBackend()));
    }

    @GetMapping("retry")
    ResponseEntity<?> retry(){
        return ResponseEntity.ok(Map.of("response",resourceServer.retry()));
    }
}
