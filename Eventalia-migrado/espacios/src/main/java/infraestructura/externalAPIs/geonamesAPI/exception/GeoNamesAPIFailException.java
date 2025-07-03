package infraestructura.externalAPIs.geonamesAPI.exception;

@SuppressWarnings("serial")
public class GeoNamesAPIFailException extends Exception {

  public GeoNamesAPIFailException(String message) {
    super(message);
  }

  public GeoNamesAPIFailException(String message, Throwable cause) {
    super(message, cause);
  }
}
