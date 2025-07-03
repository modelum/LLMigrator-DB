package infraestructura.api.rest.DTO;

public class CrearEspacioFisicoDTO {
	private String nombre;
	private String propietario;
	private int capacidad;
	private String direccionPostal;
	private double longitud;
	private double latitud;
	private String descripcion;
	
	public CrearEspacioFisicoDTO(String nombre, String propietario, int capacidad, String direccionPostal,
			double longitud, double latitud, String descripcion) {
		this.nombre = nombre;
		this.propietario = propietario;
		this.capacidad = capacidad;
		this.direccionPostal = direccionPostal;
		this.longitud = longitud;
		this.latitud = latitud;
		this.descripcion = descripcion;
	}
	
	public CrearEspacioFisicoDTO() {}
	
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
	
	public String getDireccionPostal() {
		return direccionPostal;
	}
	
	public void setDireccionPostal(String direccionPostal) {
		this.direccionPostal = direccionPostal;
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
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
