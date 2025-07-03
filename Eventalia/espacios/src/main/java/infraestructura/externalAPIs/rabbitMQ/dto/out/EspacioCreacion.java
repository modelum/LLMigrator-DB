package infraestructura.externalAPIs.rabbitMQ.dto.out;

public class EspacioCreacion extends EventoRabbit {
  
  private String nombre;
  private String propietario;
  private int capacidad;
  private String direccionPostal;
  private double longitud;
  private double latitud;
  private String descripcion;

  public EspacioCreacion() {
    super(TipoEvento.ESPACIO_CREADO, null);
  }
  
	public EspacioCreacion(String entidadId, String nombre, String propietario, int capacidad, String direccionPostal,
						   double longitud, double latitud, String descripcion) {
		super(TipoEvento.ESPACIO_CREADO, entidadId);
		this.nombre = nombre;
		this.propietario = propietario;
		this.capacidad = capacidad;
		this.direccionPostal = direccionPostal;
		this.longitud = longitud;
		this.latitud = latitud;
		this.descripcion = descripcion;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getPropietario() {
		return propietario;
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	
	public String getDireccionPostal() {
		return direccionPostal;
	}
	
	public double getLongitud() {
		return longitud;
	}
	
	public double getLatitud() {
		return latitud;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
  
}
