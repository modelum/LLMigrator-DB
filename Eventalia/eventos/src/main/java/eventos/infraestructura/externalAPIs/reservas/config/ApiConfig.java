package eventos.infraestructura.externalAPIs.reservas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfig {
  private String reservas;
  private String secretoReservas;

  public String getReservas() {
    return reservas;
  }

  public void setReservas(String reservas) {
    this.reservas = reservas;
  }

  public String getSecretoReservas() {
    return secretoReservas;
  }

  public void setSecretoReservas(String secretoReservas) {
    this.secretoReservas = secretoReservas;
  }
}
