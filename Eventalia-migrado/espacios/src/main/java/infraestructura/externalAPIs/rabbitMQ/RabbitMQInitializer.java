package infraestructura.externalAPIs.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import infraestructura.externalAPIs.rabbitMQ.config.RabbitConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class RabbitMQInitializer implements ServletContextListener {
  private static final int MAX_RETRIES = 5;
  private static Connection connection;
  private static Channel channel;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    boolean connected = false;

    while (!connected) {
      try {
        connection = RabbitConfig.crearFactoria().newConnection();
        channel = connection.createChannel();
        RabbitConfig.exchange(channel);
        RabbitConfig.queue(channel);
        RabbitConfig.bind(channel);
        connected = true;
        System.out.println("RabbitMQ conectado correctamente");
      } catch (Exception e) {
        try {
        
          System.out.println("Esperando a que RabbitMQ esté disponible...");
          Thread.sleep(3000);
        } catch (InterruptedException ex) {
          throw new RuntimeException(ex);
        }
      }
    }
  }

  public static Channel getChannel() {
    return channel;
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    try {
      if (channel != null) {
        channel.close();
      }
      if (connection != null) {
        connection.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
