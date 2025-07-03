package infraestructura.externalAPIs.rabbitMQ.implementacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import dominio.EspacioFisico;
import infraestructura.externalAPIs.rabbitMQ.PublicadorEspacios;
import infraestructura.externalAPIs.rabbitMQ.dto.out.EventoRabbit;
import infraestructura.externalAPIs.rabbitMQ.excepciones.BusEventosException;
import infraestructura.externalAPIs.rabbitMQ.mapper.EspacioRabbitMapper;
import infraestructura.externalAPIs.rabbitMQ.RabbitMQInitializer;

import java.util.Map;

import static infraestructura.externalAPIs.rabbitMQ.config.RabbitConfig.*;

public class PublicadorEspaciosImpl implements PublicadorEspacios {

  private final ObjectMapper objectMapper;

  public PublicadorEspaciosImpl() {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());
    this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  private void sendMessage(EventoRabbit evento) throws BusEventosException {
    try {
      Channel channel = RabbitMQInitializer.getChannel();

      if (channel == null || !channel.isOpen()) {
        throw new BusEventosException("El canal de Rabbit no se encuentra disponible");
      }

      byte[] mensaje = objectMapper.writeValueAsBytes(evento);

      channel.basicPublish(
          EXCHANGE_NAME,
          ROUTING_KEY + evento.getTipoEvento().getNombre(),
          new AMQP.BasicProperties.Builder()
              .contentType("application/json")
              .deliveryMode(2)
              .headers(Map.of("__TypeId__", evento.getTipoEvento().getNombre()))
              .build(),
          mensaje);

    } catch (Exception e) {
      throw new BusEventosException(
          "Error al publicar el evento de tipo " + evento.getTipoEvento().getNombre(), e);
    }
  }

  @Override
  public void publicarEspacioCreacion(EspacioFisico espacio) throws BusEventosException {
    sendMessage(EspacioRabbitMapper.toEspacioCreacion(espacio));
  }

  @Override
  public void publicarEspacioModificacion(EspacioFisico espacio) throws BusEventosException {
    sendMessage(EspacioRabbitMapper.toEspacioModificacion(espacio));
  }

  @Override
  public void publicarEspacioBorrado(String entidadId) throws BusEventosException {
    sendMessage(EspacioRabbitMapper.toEspacioCerrado(entidadId));
  }

  @Override
  public void publicarEspacioActivado(String entidadId) throws BusEventosException {
    sendMessage(EspacioRabbitMapper.toEspacioActivo(entidadId));
  }
}
