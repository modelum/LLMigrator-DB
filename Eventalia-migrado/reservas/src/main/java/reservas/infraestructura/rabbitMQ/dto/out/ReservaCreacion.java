package reservas.infraestructura.rabbitMQ.dto.out;

public class ReservaCreacion extends ReservaEventoRabbit {
  private String idUsuario;
  private String plazasReservadas;
  private String idEvento;

  public ReservaCreacion() {
    super(ReservaTipoEvento.RESERVA_CREADA, null);
  }

  public ReservaCreacion(
      String idReserva, String idUsuario, String plazasReservadas, String idEvento) {
    super(ReservaTipoEvento.RESERVA_CREADA, idReserva);
    this.idUsuario = idUsuario;
    this.plazasReservadas = plazasReservadas;
    this.idEvento = idEvento;
  }

  public ReservaCreacion(
      String idReserva, String idUsuario, int plazasReservadas, String idEvento) {
    this(idReserva, idUsuario, String.valueOf(plazasReservadas), idEvento);
  }

  public String getIdUsuario() {
    return idUsuario;
  }

  public String getPlazasReservadas() {
    return plazasReservadas;
  }

  public String getIdEvento() {
    return idEvento;
  }
}
