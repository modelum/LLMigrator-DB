package infraestructura.externalAPIs.rabbitMQ.dto.out;

public enum TipoEvento {
  ESPACIO_CREADO("espacio-creado"),
  ESPACIO_MODIFICADO("espacio-modificado"),
  ESPACIO_CANCELADO("espacio-cancelado"),
  ESPACIO_ACTIVADO("espacio-activado");

  private final String nombre;

  TipoEvento(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return nombre;
  }
}
