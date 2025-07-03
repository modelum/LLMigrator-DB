package eventos.infraestructura.rabbitMQ.implementacion;

import eventos.dominio.Evento;
import eventos.infraestructura.rabbitMQ.PublicadorEventos;
import eventos.infraestructura.rabbitMQ.config.RabbitMQConfig;
import eventos.infraestructura.rabbitMQ.dto.EventoRabbit;
import eventos.infraestructura.rabbitMQ.dto.TipoEvento;
import eventos.infraestructura.rabbitMQ.mapper.EventoRabbitMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Component;

@Component
public class PublicadorEventosImpl implements PublicadorEventos {

  private final AmqpTemplate rabbitTemplate;
  private final TopicExchange topicExchange;

  public PublicadorEventosImpl(AmqpTemplate rabbitTemplate, TopicExchange topicExchange) {
    this.rabbitTemplate = rabbitTemplate;
    this.topicExchange = topicExchange;
  }

  @Override
  public void publicarEventoCreacion(Evento evento) {
    publicarEvento(
        EventoRabbitMapper.toEventoCreacion(evento), TipoEvento.EVENTO_CREADO.getNombre());
  }

  @Override
  public void publicarEventoModificacion(Evento evento) {
    publicarEvento(
        EventoRabbitMapper.toEventoModificacion(evento), TipoEvento.EVENTO_MODIFICADO.getNombre());
  }

  @Override
  public void publicarEventoCancelado(String entidadId) {
    publicarEvento(
        EventoRabbitMapper.toEventoBorrado(entidadId), TipoEvento.EVENTO_CANCELADO.getNombre());
  }

  private void publicarEvento(EventoRabbit eventoRabbit, String tipoEvento) {
    this.rabbitTemplate.convertAndSend(
        topicExchange.getName(),
        RabbitMQConfig.ROUTING_KEY + tipoEvento,
        eventoRabbit,
        message -> {
          message.getMessageProperties().setHeader("__TypeId__", tipoEvento);
          return message;
        });
  }
}
