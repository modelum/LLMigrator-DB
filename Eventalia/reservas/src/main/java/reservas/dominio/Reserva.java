package reservas.dominio;

import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = "reservas")
public class Reserva {
  @MongoId(FieldType.STRING)
  private UUID id;

  @Field(name = "idUsuario", targetType = FieldType.STRING)
  private UUID idUsuario;

  private boolean cancelado;
  private int plazasReservadas;
  @DBRef private Evento evento;

  public Reserva(UUID idUsuario, int plazasReservadas, Evento evento) {
    this.id = UUID.randomUUID();
    this.idUsuario = idUsuario;
    this.plazasReservadas = plazasReservadas;
    this.evento = evento;
    this.cancelado = false;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(UUID idUsuario) {
    this.idUsuario = idUsuario;
  }

  public int getPlazasReservadas() {
    return plazasReservadas;
  }

  public void setPlazasReservadas(int plazasReservadas) {
    this.plazasReservadas = plazasReservadas;
  }

  public Evento getEvento() {
    return evento;
  }

  public void setEvento(Evento evento) {
    this.evento = evento;
  }

  public boolean isCancelado() {
    return cancelado;
  }

  public void setCancelado(boolean cancelado) {
    this.cancelado = cancelado;
  }

  public void cancelar() {
    this.setCancelado(true);
  }
}
