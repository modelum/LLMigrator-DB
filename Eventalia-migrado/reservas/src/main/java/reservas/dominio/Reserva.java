package reservas.dominio;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "RESERVA")
public class Reserva {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "id_usuario", nullable = false)
  private UUID idUsuario;

  @Column(name = "cancelado", nullable = false)
  private boolean cancelado;
  @Column(name = "plazas_reservadas", nullable = false)
  private int plazasReservadas;

  @ManyToOne
  @JoinColumn(name = "evento")
  private Evento evento;

  public Reserva(UUID idUsuario, int plazasReservadas, Evento evento) {
    this.idUsuario = idUsuario;
    this.plazasReservadas = plazasReservadas;
    this.evento = evento;
    this.cancelado = false;
  }

  public Reserva() {
    // Constructor por defecto necesario para JPA
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
