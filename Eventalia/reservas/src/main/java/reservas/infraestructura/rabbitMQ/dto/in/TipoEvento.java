package reservas.infraestructura.rabbitMQ.dto.in;

public enum TipoEvento {
  EVENTO_CREADO("evento-creado"),
  EVENTO_MODIFICADO("evento-modificado"),
  EVENTO_CANCELADO("evento-cancelado");

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
