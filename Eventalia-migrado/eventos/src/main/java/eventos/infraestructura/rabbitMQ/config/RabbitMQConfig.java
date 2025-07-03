package eventos.infraestructura.rabbitMQ.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eventos.infraestructura.rabbitMQ.dto.TipoEvento;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig {
  public static final String EXCHANGE_NAME = "bus";
  public static final String QUEUE_NAME = "eventos";
  public static final String ROUTING_KEY = "bus.eventos.";
  public static final String BINDING_KEY_ESPACIOS = "bus.espacios.#";

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
    return BindingBuilder.bind(queue).to(exchange).with(BINDING_KEY_ESPACIOS);
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
            TipoEvento.ESPACIO_CREADO.getNombre(),
            eventos.infraestructura.rabbitMQ.dto.in.EspacioCreacion.class,
            TipoEvento.ESPACIO_MODIFICADO.getNombre(),
            eventos.infraestructura.rabbitMQ.dto.in.EspacioModificacion.class,
            TipoEvento.ESPACIO_CANCELADO.getNombre(),
            eventos.infraestructura.rabbitMQ.dto.in.EspacioCerrado.class,
            TipoEvento.ESPACIO_ACTIVADO.getNombre(),
            eventos.infraestructura.rabbitMQ.dto.in.EspacioActivado.class));
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
