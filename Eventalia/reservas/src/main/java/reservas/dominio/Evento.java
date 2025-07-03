package reservas.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "eventos")
public class Evento {

  @MongoId(FieldType.STRING)
  private UUID id;

  private int plazasDisponibles;
  private boolean cancelado;
  private LocalDateTime fechaInicio;
  private String nombreEvento;
  @DBRef private List<Reserva> reservas;

  public Evento(UUID id, int plazasDisponibles, boolean cancelado, LocalDateTime fechaInicio, String nombreEvento) {
    this.id = id;
    this.plazasDisponibles = plazasDisponibles;
    this.cancelado = cancelado;
    this.fechaInicio = fechaInicio;
    this.nombreEvento = nombreEvento;
    this.reservas = new ArrayList<>();
  }

  public boolean add(Reserva reserva) {
    if (reserva == null) {
      throw new IllegalArgumentException("La reserva no puede ser nula");
    }
    if (this.plazasDisponibles < reserva.getPlazasReservadas()) {
      throw new IllegalArgumentException("No hay plazas disponibles");
    }
    return this.reservas.add(reserva);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getPlazasReservadas() {
    return this.reservas.stream().mapToInt(Reserva::getPlazasReservadas).sum();
  }

  public int getPlazasDisponibles() {
    return plazasDisponibles;
  }

  public void setPlazasDisponibles(int plazasDisponibles) {
    this.plazasDisponibles = plazasDisponibles;
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

  public List<Reserva> getReservas() {
    return Collections.unmodifiableList(reservas);
  }

  public void setReservas(List<Reserva> reservas) {
    this.reservas = reservas;
  }

  public LocalDateTime getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(LocalDateTime fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public String getNombreEvento() {
    return nombreEvento;
  }

  public void setNombreEvento(String nombreEvento) {
    this.nombreEvento = nombreEvento;
  }
}
