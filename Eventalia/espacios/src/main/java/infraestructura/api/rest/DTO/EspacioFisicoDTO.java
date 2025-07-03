package infraestructura.api.rest.DTO;

import dominio.enumerados.EstadoEspacioFisico;
import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
public class EspacioFisicoDTO implements Serializable {

  private UUID id;
  private String nombre;
  private int capacidad;
  private String direccion;
  private String descripcion;
  private EstadoEspacioFisico estado;

  public EspacioFisicoDTO() {}

  public EspacioFisicoDTO(
      UUID id,
      String nombre,
      int capacidad,
      String direccion,
      String descripcion,
      EstadoEspacioFisico estado) {
    this.id = id;
    this.nombre = nombre;
    this.capacidad = capacidad;
    this.direccion = direccion;
    this.descripcion = descripcion;
    this.estado = estado;
  }

  public UUID getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public int getCapacidad() {
    return capacidad;
  }

  public String getDireccion() {
    return direccion;
  }

  public EstadoEspacioFisico getEstado() {
    return estado;
  }

  public void setEstado(EstadoEspacioFisico estado) {
    this.estado = estado;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setCapacidad(int capacidad) {
    this.capacidad = capacidad;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
}
