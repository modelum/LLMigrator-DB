package eventos;

import eventos.infraestructura.externalAPIs.reservas.config.ApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApiConfig.class)
public class EventosApplication {
  public static void main(String[] args) {
    SpringApplication.run(EventosApplication.class, args);
  }
}
