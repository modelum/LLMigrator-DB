package infraestructura.repositorio.excepciones;

@SuppressWarnings("serial")
public class EntidadNoEncontrada extends Exception {

  public EntidadNoEncontrada(String mensaje) {
    super(mensaje);
  }

  public EntidadNoEncontrada(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }
}
