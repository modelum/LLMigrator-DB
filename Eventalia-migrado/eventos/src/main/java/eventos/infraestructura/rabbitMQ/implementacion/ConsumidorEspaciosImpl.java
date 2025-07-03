package eventos.infraestructura.rabbitMQ.implementacion;

import eventos.infraestructura.rabbitMQ.config.RabbitMQConfig;
import eventos.infraestructura.rabbitMQ.dto.EventoRabbit;
import eventos.infraestructura.rabbitMQ.dto.in.EspacioActivado;
import eventos.infraestructura.rabbitMQ.dto.in.EspacioCerrado;
import eventos.infraestructura.rabbitMQ.dto.in.EspacioCreacion;
import eventos.infraestructura.rabbitMQ.dto.in.EspacioModificacion;
import eventos.servicios.ServicioDespachadorEspacios;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsumidorEspaciosImpl {

  private final ServicioDespachadorEspacios servicioDespachadorEspacios;

  public ConsumidorEspaciosImpl(final ServicioDespachadorEspacios servicioDespachadorEspacios) {
    this.servicioDespachadorEspacios = servicioDespachadorEspacios;
  }

  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
  public void consumir(EventoRabbit evento) throws Exception {
    switch (evento.getTipoEvento()) {
      case ESPACIO_CREADO:
        EspacioCreacion espacioCreacion = (EspacioCreacion) evento;
        servicioDespachadorEspacios.despacharEspacioFisicoCreado(
            UUID.fromString(espacioCreacion.getEntidadId()),
            espacioCreacion.getNombre(),
            espacioCreacion.getDescripcion(),
            espacioCreacion.getCapacidad(),
            espacioCreacion.getDireccionPostal());
        break;
      case ESPACIO_MODIFICADO:
        EspacioModificacion espacioModificacion = (EspacioModificacion) evento;
        servicioDespachadorEspacios.despacharEspacioFisicoModificado(
            UUID.fromString(espacioModificacion.getEntidadId()),
            espacioModificacion.getNombre(),
            espacioModificacion.getCapacidad());
        break;
      case ESPACIO_CANCELADO:
        EspacioCerrado espacioCerrado = (EspacioCerrado) evento;
        servicioDespachadorEspacios.despacharEspacioFisicoCerrado(
            UUID.fromString(espacioCerrado.getEntidadId()));
        break;
      case ESPACIO_ACTIVADO:
        EspacioActivado espacioActivado = (EspacioActivado) evento;
        servicioDespachadorEspacios.despacharEspacioFisicoActivado(
            UUID.fromString(espacioActivado.getEntidadId()));
        break;
      default:
        throw new IllegalArgumentException(
            "Tipo de evento no soportado: " + evento.getTipoEvento());
    }
  }
}
