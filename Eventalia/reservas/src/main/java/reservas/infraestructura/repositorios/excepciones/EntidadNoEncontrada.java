package reservas.infraestructura.repositorios.excepciones;

@SuppressWarnings("serial")
public class EntidadNoEncontrada extends RuntimeException {

  public EntidadNoEncontrada(String mensaje) {
    super(mensaje);
  }

  public EntidadNoEncontrada(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
