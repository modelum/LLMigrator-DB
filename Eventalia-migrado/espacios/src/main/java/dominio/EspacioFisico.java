package dominio;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import dominio.enumerados.EstadoEspacioFisico;
import infraestructura.repositorio.Identificable;

@Entity
public class EspacioFisico implements Identificable {

	@Id
	@Convert(converter = UUIDConverter.class) // Usa el convertidor para almacenar UUID como String
	@Column(length = 36, nullable = false, unique = true)
	private UUID id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String propietario;

	@Column(nullable = false)
	private int capacidad;

	@Column(nullable = false)
	private double longitud;

	@Column(nullable = false)
	private double latitud;

	@Column(nullable = false)
	private String direccion;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "puntos_interes", joinColumns = @JoinColumn(name = "espacio_fisico_id"))
	private Collection<PuntoInteres> puntosInteres;

	@Column(nullable = false)
	@Lob
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoEspacioFisico estado;

	public EspacioFisico() {
	}

	public EspacioFisico(String nombre, String propietario, int capacidad, double longitud, double latitud,
			String direccion, String descripcion, PuntoInteres... puntosInteres) {
		this.id = UUID.randomUUID();
		this.nombre = nombre;
		this.propietario = propietario;
		this.capacidad = capacidad;
		this.longitud = longitud;
		this.latitud = latitud;
		this.direccion = direccion;
		this.puntosInteres = new HashSet<PuntoInteres>(); // Para evitar tener puntos de interes repetidos
		if (puntosInteres != null) {
			this.puntosInteres.addAll(Arrays.asList(puntosInteres));
		}
		this.descripcion = descripcion;
		this.estado = EstadoEspacioFisico.ACTIVO;
	}

	public boolean addPuntoInteres(PuntoInteres puntoInteres) {
		if (puntoInteres == null) {
			throw new IllegalArgumentException("El punto de interes no puede ser nulo.");
		}
		return puntosInteres.add(puntoInteres);
	}

	public boolean removePuntoInteres(PuntoInteres puntoInteres) {
		if (puntoInteres == null) {
			throw new IllegalArgumentException("El punto de interes no puede ser nulo.");
		}
		return puntosInteres.remove(puntoInteres);
	}

	// Setters y getters

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Collection<PuntoInteres> getPuntosInteres() {
		return puntosInteres;
	}

	public void setPuntosInteres(Set<PuntoInteres> puntosInteres) {
		this.puntosInteres = puntosInteres;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoEspacioFisico getEstado() {
		return estado;
	}

	public void setEstado(EstadoEspacioFisico estado) {
		this.estado = estado;
	}

	// HashCode y Equals

	@Override
	public int hashCode() {
		return Objects.hash(capacidad, descripcion, direccion, estado, id, latitud, longitud, nombre, propietario,
				puntosInteres);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EspacioFisico other = (EspacioFisico) obj;
		return capacidad == other.capacidad && Objects.equals(descripcion, other.descripcion)
				&& Objects.equals(direccion, other.direccion) && estado == other.estado && Objects.equals(id, other.id)
				&& Double.doubleToLongBits(latitud) == Double.doubleToLongBits(other.latitud)
				&& Double.doubleToLongBits(longitud) == Double.doubleToLongBits(other.longitud)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(propietario, other.propietario)
				&& Objects.equals(puntosInteres, other.puntosInteres);
	}
}
