package reservas.infraestructura.rabbitMQ.dto.out;

public enum ReservaTipoEvento {
  RESERVA_CREADA("reserva-creada");

  private final String nombre;

  ReservaTipoEvento(String nombre) {
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
