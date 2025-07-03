package reservas.infraestructura.rabbitMQ.implementacion;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reservas.dominio.Reserva;
import reservas.infraestructura.rabbitMQ.PublicadorEventos;
import reservas.infraestructura.rabbitMQ.config.RabbitMQConfig;
import reservas.infraestructura.rabbitMQ.dto.out.ReservaTipoEvento;

@Component
public class PublicadorEventosImpl implements PublicadorEventos {
  private final RabbitTemplate rabbitTemplate;
  private final TopicExchange topicExchange;

  public PublicadorEventosImpl(RabbitTemplate rabbitTemplate, TopicExchange topicExchange) {
    this.rabbitTemplate = rabbitTemplate;
    this.topicExchange = topicExchange;
  }

  @Override
  public void publicarCreacionReserva(Reserva reserva) throws Exception {
    this.rabbitTemplate.convertAndSend(
        topicExchange.getName(),
        RabbitMQConfig.ROUTING_KEY_RESERVAS + ReservaTipoEvento.RESERVA_CREADA.getNombre());
  }
}
