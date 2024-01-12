package cicuitbreaker.springretry.common.exception;

public class RemoteServiceNotAvailableException extends RuntimeException {

  public RemoteServiceNotAvailableException(String msg) {
    super(msg);
  }

}