package eventos.servicios;

import eventos.infraestructura.repositorios.excepciones.EntidadNoEncontrada;

import java.util.UUID;

public interface ServicioDespachadorEspacios {
  public void despacharEspacioFisicoCreado(
      UUID id, String nombre, String descripcion, int capacidad, String direccion);

  public void despacharEspacioFisicoModificado(
      UUID id, String nombre, int capacidad) throws EntidadNoEncontrada;

  public void despacharEspacioFisicoCerrado(UUID id) throws EntidadNoEncontrada;

  public void despacharEspacioFisicoActivado(UUID id) throws EntidadNoEncontrada;
}
