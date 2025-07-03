package reservas.infraestructura.rabbitMQ.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reservas.infraestructura.rabbitMQ.dto.in.TipoEvento;

import java.util.Map;

@Configuration
public class RabbitMQConfig {
  public static final String QUEUE_NAME = "reservas";
  public static final String BINDING_KEY_EVENTOS = "bus.eventos.#";
  public static final String EXCHANGE_NAME = "bus";
  public static final String ROUTING_KEY_RESERVAS = "bus.reservas.";

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  public Queue queue() {
    boolean durable = true;
    boolean exclusive = false;
    boolean autoDelete = false;
    return new Queue(QUEUE_NAME, durable, exclusive, autoDelete);
  }

  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(BINDING_KEY_EVENTOS);
  }

  @Bean
  public MessageConverter messageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

    DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
    typeMapper.setIdClassMapping(
        Map.of(
            TipoEvento.EVENTO_CREADO.getNombre(),
            reservas.infraestructura.rabbitMQ.dto.in.EventoCreacion.class,
            TipoEvento.EVENTO_MODIFICADO.getNombre(),
            reservas.infraestructura.rabbitMQ.dto.in.EventoModificacion.class,
            TipoEvento.EVENTO_CANCELADO.getNombre(),
            reservas.infraestructura.rabbitMQ.dto.in.EventoBorrado.class));

    converter.setJavaTypeMapper(typeMapper);

    return converter;
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }
}
