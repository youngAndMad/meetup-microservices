package cicuitbreaker.cicruitbreaker.service;

public interface ResourceServer {

    String getResponseFromBackend();

    String retry();
}
