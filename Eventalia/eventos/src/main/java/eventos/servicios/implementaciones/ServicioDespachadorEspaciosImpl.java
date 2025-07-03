package eventos.servicios.implementaciones;

import eventos.dominio.EspacioFisico;
import eventos.dominio.enumerados.EstadoEspacioFisico;
import eventos.infraestructura.rabbitMQ.PublicadorEventos;
import eventos.infraestructura.repositorios.espacios.RepositorioEspacios;
import eventos.infraestructura.repositorios.eventos.RepositorioEventos;
import eventos.infraestructura.repositorios.excepciones.EntidadNoEncontrada;
import eventos.servicios.ServicioDespachadorEspacios;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ServicioDespachadorEspaciosImpl implements ServicioDespachadorEspacios {

  private final RepositorioEspacios repositorioEspacios;
  private final RepositorioEventos repositorioEventos;
  private final PublicadorEventos publicadorEventos;

  public ServicioDespachadorEspaciosImpl(
      RepositorioEspacios repositorioEspacios, RepositorioEventos repositorioEventos, PublicadorEventos publicadorEventos) {
    this.repositorioEspacios = repositorioEspacios;
    this.repositorioEventos = repositorioEventos;
    this.publicadorEventos = publicadorEventos;
  }

  @Override
  public void despacharEspacioFisicoCreado(
      UUID id, String nombre, String descripcion, int capacidad, String direccion) {
    this.repositorioEspacios.save(
        new EspacioFisico(id, nombre, capacidad, EstadoEspacioFisico.ACTIVO, direccion));
  }

  @Override
  public void despacharEspacioFisicoModificado(UUID id, String nombre, int capacidad)
      throws EntidadNoEncontrada {
    EspacioFisico espacio =
        this.repositorioEspacios
            .findById(id)
            .orElseThrow(() -> new EntidadNoEncontrada("Espacio no encontrado"));
    if (!nombre.equals(espacio.getNombre())) {
      espacio.setNombre(nombre);
    }
    if (capacidad != espacio.getCapacidad()) {
      espacio.setCapacidad(capacidad);
    }
    this.repositorioEspacios.save(espacio);
  }

  @Override
  public void despacharEspacioFisicoCerrado(UUID id) throws EntidadNoEncontrada {
    EspacioFisico espacio =
        this.repositorioEspacios
            .findById(id)
            .orElseThrow(() -> new EntidadNoEncontrada("Espacio no encontrado"));
    espacio.setEstado(EstadoEspacioFisico.CERRADO_TEMPORALMENTE);
    this.repositorioEventos
        .getEventosPorEspacio(id)
        .forEach(
            evento -> {
              evento.cancelar();
              this.repositorioEventos.save(evento);
              this.publicadorEventos.publicarEventoCancelado(evento.getId().toString());
            });
    this.repositorioEspacios.save(espacio);
  }

  @Override
  public void despacharEspacioFisicoActivado(UUID id) throws EntidadNoEncontrada {
    EspacioFisico espacio =
        this.repositorioEspacios
            .findById(id)
            .orElseThrow(() -> new EntidadNoEncontrada("Espacio no encontrado"));
    espacio.setEstado(EstadoEspacioFisico.ACTIVO);
    this.repositorioEspacios.save(espacio);
  }
}
