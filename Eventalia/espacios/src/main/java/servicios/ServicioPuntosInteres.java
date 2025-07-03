package servicios;

import dominio.PuntoInteres;
import infraestructura.externalAPIs.geonamesAPI.exception.GeoNamesAPIFailException;

import java.util.List;

public interface ServicioPuntosInteres {
  /**
   * Devuelve los puntos de interés cercanos a una ubicación dada.
   *
   * @param latitud Latitud de la ubicación.
   * @param longitud Longitud de la ubicación.
   * @return Lista de puntos de interés cercanos.
   */
  List<PuntoInteres> getPuntosInteres(final double latitud, final double longitud)
      throws GeoNamesAPIFailException;
}
