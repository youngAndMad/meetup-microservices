package cicuitbreaker.springretry.client;

import cicuitbreaker.springretry.common.exception.RemoteServiceNotAvailableException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface BackendAdapter {

    @Retryable(
            retryFor = {RemoteServiceNotAvailableException.class},
            maxAttempts = 3,
            backoff = @Backoff(
                    delay = 1000,
                    multiplier = 1.6
            ),
            noRetryFor = {NumberFormatException.class},
            notRecoverable = {NullPointerException.class}
    )
    String getBackendResponse();

    @Recover
    String getBackendResponseFallback(RemoteServiceNotAvailableException e);
}