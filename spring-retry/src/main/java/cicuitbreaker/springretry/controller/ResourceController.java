package cicuitbreaker.springretry.controller;

import cicuitbreaker.springretry.client.BackendAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ResourceController {

    private final BackendAdapter backendAdapter;

    public ResourceController(BackendAdapter backendAdapter) {
        this.backendAdapter = backendAdapter;
    }

    @GetMapping("/retryable-operation")
    ResponseEntity<?> retry() {
        String apiResponse = backendAdapter.getBackendResponse();
        return ResponseEntity.ok().body(Map.of("response", apiResponse));
    }

}