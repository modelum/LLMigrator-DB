package infraestructura.externalAPIs.geonamesAPI.implementacion;

import infraestructura.externalAPIs.geonamesAPI.exception.GeoNamesAPIFailException;
import infraestructura.externalAPIs.geonamesAPI.GeoNamesAPI;
import dominio.PuntoInteres;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GeoNamesAPIImpl implements GeoNamesAPI {

  private static final String USERNAME = "f.soladuran";

  @Override
  public List<PuntoInteres> findNearByPuntosInteres(final double latitud, final double longitud)
      throws GeoNamesAPIFailException {
    List<PuntoInteres> puntosInteresOrdenados = new LinkedList<>();

    String urlString = "http://api.geonames.org/findNearbyWikipedia?username="+USERNAME+"&lat="+latitud+"&lng="+longitud;

    HttpClient cliente = HttpClient.newHttpClient();

    HttpRequest peticion = HttpRequest.newBuilder().uri(URI.create(urlString)).GET().build();

    try {
      HttpResponse<InputStream> respuesta =
          cliente.send(peticion, HttpResponse.BodyHandlers.ofInputStream());

      if (respuesta.statusCode() == 200) {
        puntosInteresOrdenados =
            parse(respuesta.body()).stream()
                .sorted((p1, p2) -> Double.compare(p1.getDistancia(), p2.getDistancia()))
                .collect(Collectors.toList());

      } else {
        throw new GeoNamesAPIFailException("Error al obtener los puntos de interés cercanos");
      }
    } catch (SAXException | IOException | ParserConfigurationException | InterruptedException e) {
      throw new GeoNamesAPIFailException("Error al obtener los puntos de interés cercanos");
    }

    return puntosInteresOrdenados;
  }

  private List<PuntoInteres> parse(InputStream inputStream)
      throws ParserConfigurationException, IOException, SAXException {
    List<PuntoInteres> puntosInteres = new LinkedList<>();

    DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
    DocumentBuilder analizador = factoria.newDocumentBuilder();
    Document documento = analizador.parse(inputStream);

    documento.getDocumentElement().normalize();

    NodeList elementos = documento.getElementsByTagName("entry");

    for (int i = 0; i < elementos.getLength(); i++) {
      Node nodo = elementos.item(i);

      if (nodo.getNodeType() == Node.ELEMENT_NODE) {
        Element elemento = (Element) nodo;

        String nombre = getTextoElemento(elemento, "title");
        String descripcion = getTextoElemento(elemento, "summary");
        double distancia = Double.parseDouble(getTextoElemento(elemento, "distance"));
        String url = getTextoElemento(elemento, "wikipediaUrl");

        puntosInteres.add(new PuntoInteres(nombre, descripcion, distancia, url));
      }
    }

    return puntosInteres;
  }

  private String getTextoElemento(Element elemento, String etiqueta) {
    NodeList nodeList = elemento.getElementsByTagName(etiqueta);
    return (nodeList.getLength() > 0 && nodeList.item(0) != null)
        ? nodeList.item(0).getTextContent()
        : "";
  }
}
