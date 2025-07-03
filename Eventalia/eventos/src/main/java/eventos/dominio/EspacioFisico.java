package eventos.dominio;

import eventos.dominio.enumerados.EstadoEspacioFisico;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.Type;

@Entity
public class EspacioFisico {

  @Id
  @SuppressWarnings("deprecation")
  @Type(type = "uuid-char")
  @Column(columnDefinition = "VARCHAR(36)", nullable = false, unique = true)
  private UUID id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private int capacidad;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EstadoEspacioFisico estado;

  @Column(nullable = false)
  private String direccion;

  public EspacioFisico() {}

  public EspacioFisico(
      UUID id, String nombre, int capacidad, EstadoEspacioFisico estado, String direccion) {
    this.id = id;
    this.nombre = nombre;
    this.capacidad = capacidad;
    this.estado = estado;
    this.direccion = direccion;
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

  public int getCapacidad() {
    return capacidad;
  }

  public void setCapacidad(int capacidad) {
    this.capacidad = capacidad;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public EstadoEspacioFisico getEstado() {
    return estado;
  }

  public void setEstado(EstadoEspacioFisico estado) {
    this.estado = estado;
  }

  // HashCode y Equals

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    EspacioFisico that = (EspacioFisico) o;
    return capacidad == that.capacidad
        && Objects.equals(id, that.id)
        && Objects.equals(nombre, that.nombre)
        && estado == that.estado
        && Objects.equals(direccion, that.direccion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, capacidad, estado, direccion);
  }
}
