package dominio;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class PuntoInteres {

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  @Lob
  private String descripcion;

  @Column(nullable = false)
  private double distancia;

  @Column(nullable = false)
  private String url;

  public PuntoInteres() {}

  public PuntoInteres(String nombre, String descripcion, double distancia, String url) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.distancia = distancia;
    this.url = url;
  }

  // Getters y Setters

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public double getDistancia() {
    return distancia;
  }

  public void setDistancia(double distancia) {
    this.distancia = distancia;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  // HashCode y Equals

  @Override
  public int hashCode() {
    return Objects.hash(descripcion, distancia, nombre, url);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PuntoInteres other = (PuntoInteres) obj;
    return Objects.equals(descripcion, other.descripcion)
        && Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
        && Objects.equals(nombre, other.nombre)
        && Objects.equals(url, other.url);
  }

  @Override
  public String toString() {
    return "PuntoInteres{" +
            "nombre='" + nombre + '\'' +
            ", descripcion='" + descripcion + '\'' +
            ", distancia=" + distancia +
            ", url='" + url + '\'' +
            '}';
  }
}
