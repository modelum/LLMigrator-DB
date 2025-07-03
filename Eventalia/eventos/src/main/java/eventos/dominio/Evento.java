package eventos.dominio;

import eventos.dominio.enumerados.Categoria;

import javax.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Evento {

  @Id
  @SuppressWarnings("deprecation")
  @Type(type = "uuid-char")
  @Column(columnDefinition = "VARCHAR(36)", nullable = false, unique = true)
  private UUID id;

  @Column(nullable = false)
  private String nombre;

  @Lob
  @Column(nullable = false)
  private String descripcion;

  @Column(nullable = false)
  private String organizador;

  @Column(nullable = false)
  private int plazas;

  @Column(nullable = false)
  private boolean cancelado;

  @Embedded private Ocupacion ocupacion;

  @Enumerated(EnumType.STRING)
  private Categoria categoria;

  public Evento() {}

  public Evento(
      String nombre,
      String descripcion,
      String organizador,
      int plazas,
      Ocupacion ocupacion,
      Categoria categoria) {
    this.id = UUID.randomUUID();
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.organizador = organizador;
    this.plazas = plazas;
    this.cancelado = false;
    this.ocupacion = ocupacion;
    this.categoria = categoria;
  }

  public Evento(
      String nombre,
      String descripcion,
      String organizador,
      int plazas,
      LocalDateTime fechaInicio,
      LocalDateTime fechaFin,
      EspacioFisico espacioFisico,
      Categoria categoria) {
    this(
        nombre,
        descripcion,
        organizador,
        plazas,
        new Ocupacion(fechaInicio, fechaFin, espacioFisico),
        categoria);
  }

  // Setters y getters

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getOrganizador() {
    return organizador;
  }

  public void setOrganizador(String organizador) {
    this.organizador = organizador;
  }

  public int getPlazas() {
    return plazas;
  }

  public void setPlazas(int plazas) {
    this.plazas = plazas;
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

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public Ocupacion getOcupacion() {
    return ocupacion;
  }

  public void setOcupacion(Ocupacion ocupacion) {
    this.ocupacion = ocupacion;
  }

  public LocalDateTime getFechaInicio() {
    return ocupacion.getFechaInicio();
  }

  public void setFechaInicio(LocalDateTime fechaInicio) {
    this.ocupacion.setFechaInicio(fechaInicio);
  }

  public LocalDateTime getFechaFin() {
    return ocupacion.getFechaFin();
  }

  public void setFechaFin(LocalDateTime fechaFin) {
    this.ocupacion.setFechaFin(fechaFin);
  }

  public String getNombreEspacioFisico() {
    return ocupacion.getNombreEspacioFisico();
  }

  public EspacioFisico getEspacioFisico() {
    return ocupacion.getEspacioFisico();
  }

  public void setEspacioFisico(EspacioFisico espacioFisico) {
    this.ocupacion.setEspacioFisico(espacioFisico);
  }

  // HashCode y Equals

  @Override
  public int hashCode() {
    return Objects.hash(
        cancelado, categoria, descripcion, id, nombre, ocupacion, organizador, plazas);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Evento other = (Evento) obj;
    return cancelado == other.cancelado
        && categoria == other.categoria
        && Objects.equals(descripcion, other.descripcion)
        && Objects.equals(id, other.id)
        && Objects.equals(nombre, other.nombre)
        && Objects.equals(ocupacion, other.ocupacion)
        && Objects.equals(organizador, other.organizador)
        && plazas == other.plazas;
  }
}
