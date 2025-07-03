package reservas.infraestructura.rabbitMQ.implementacion;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reservas.infraestructura.rabbitMQ.config.RabbitMQConfig;
import reservas.infraestructura.rabbitMQ.dto.in.EventoCreacion;
import reservas.infraestructura.rabbitMQ.dto.in.EventoModificacion;
import reservas.infraestructura.rabbitMQ.dto.in.EventoRabbit;
import reservas.servicios.ServicioDespachadorEventos;

@Component
public class ConsumidorEventosImpl {

  private final ServicioDespachadorEventos servicioDespachadorEventos;

  public ConsumidorEventosImpl(ServicioDespachadorEventos servicioDespachadorEventos) {
    this.servicioDespachadorEventos = servicioDespachadorEventos;
  }

  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
  public void consumir(EventoRabbit eventoRabbit) throws Exception {
    switch (eventoRabbit.getTipoEvento()) {
      case EVENTO_CREADO:
        EventoCreacion eventoCreacion = (EventoCreacion) eventoRabbit;
        servicioDespachadorEventos.despacharCreacionEvento(
            UUID.fromString(eventoCreacion.getEntidadId()),
            eventoCreacion.getPlazas(),
            eventoCreacion.isCancelado(),
            LocalDateTime.parse(eventoCreacion.getFechaInicio()),
            eventoCreacion.getNombre());
        break;
      case EVENTO_MODIFICADO:
        EventoModificacion eventoModificacion = (EventoModificacion) eventoRabbit;
        servicioDespachadorEventos.despacharModificacionEvento(
            UUID.fromString(eventoModificacion.getEntidadId()),
            eventoModificacion.getPlazas(),
            LocalDateTime.parse(eventoModificacion.getFechaInicio()));
        break;
      case EVENTO_CANCELADO:
        servicioDespachadorEventos.despacharCancelacionEvento(
            UUID.fromString(eventoRabbit.getEntidadId()));
        break;
    }
  }
}
