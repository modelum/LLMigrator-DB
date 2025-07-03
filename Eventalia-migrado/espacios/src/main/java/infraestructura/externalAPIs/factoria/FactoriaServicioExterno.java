package infraestructura.externalAPIs.factoria;

import utils.PropertiesReader;

public class FactoriaServicioExterno {

  private static final String PROPERTIES = "serviciosexternos.properties";

  @SuppressWarnings("unchecked")
  public static <S> S getServicioExterno(Class<S> servicioExterno) {
    try {
      PropertiesReader properties = new PropertiesReader(PROPERTIES);
      String clase = properties.getProperty(servicioExterno.getName());
      return (S) Class.forName(clase).getConstructor().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(
          "No se ha podido obtener el servicio externo para la entidad: "
              + servicioExterno.getName());
    }
  }
}
