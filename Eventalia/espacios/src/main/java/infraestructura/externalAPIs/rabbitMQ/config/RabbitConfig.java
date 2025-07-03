package infraestructura.externalAPIs.rabbitMQ.config;

import java.io.IOException;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitConfig {
	
	public static final String URI = System.getenv("RABBITMQ_URI");
	public static final String EXCHANGE_NAME = "bus";
	public static final String QUEUE_NAME = "espacios";
	public static final String ROUTING_KEY = "bus.espacios.";
	public static final String BINDING_KEY_ESPACIOS = "bus.eventos.#";
	
	public static ConnectionFactory crearFactoria() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri(URI);
        return factory;
	}
	
	public static void queue(Channel channel) throws IOException {
		boolean durable = true;
		boolean exclusive = false;
	    boolean autoDelete = false;
		channel.queueDeclare(QUEUE_NAME, durable, exclusive, autoDelete, null);
	}
	
	public static void bind(Channel channel) throws IOException {
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, BINDING_KEY_ESPACIOS);
	}
	
	public static void exchange(Channel channel) throws IOException {
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
	}

}
