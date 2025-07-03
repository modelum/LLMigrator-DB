package eventos.infraestructura.rabbitMQ.dto;

public enum TipoEvento {
  EVENTO_CREADO("evento-creado"),
  EVENTO_MODIFICADO("evento-modificado"),
  EVENTO_CANCELADO("evento-cancelado"),

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
