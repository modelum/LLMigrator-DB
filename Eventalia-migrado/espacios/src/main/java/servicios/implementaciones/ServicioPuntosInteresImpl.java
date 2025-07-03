package servicios.implementaciones;

import dominio.PuntoInteres;
import infraestructura.externalAPIs.factoria.FactoriaServicioExterno;
import infraestructura.externalAPIs.geonamesAPI.GeoNamesAPI;

import java.util.List;

import infraestructura.externalAPIs.geonamesAPI.exception.GeoNamesAPIFailException;
import servicios.ServicioPuntosInteres;

public class ServicioPuntosInteresImpl implements ServicioPuntosInteres {

  private final GeoNamesAPI geoNamesAPI =
      FactoriaServicioExterno.getServicioExterno(GeoNamesAPI.class);

  @Override
  public List<PuntoInteres> getPuntosInteres(double latitud, double longitud)
      throws GeoNamesAPIFailException {
    if (latitud < -90 || latitud > 90) {
      throw new IllegalArgumentException("La latitud debe estar entre -90 y 90 grados");
    }

    if (longitud < -180 || longitud > 180) {
      throw new IllegalArgumentException("La longitud debe estar entre -180 y 180 grados");
    }

    return geoNamesAPI.findNearByPuntosInteres(latitud, longitud);
  }
}
