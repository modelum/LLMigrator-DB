package infraestructura.repositorio.excepciones;

@SuppressWarnings("serial")
public class RepositorioException extends Exception {

  public RepositorioException(String mensaje) {
    super(mensaje);
  }

  public RepositorioException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
