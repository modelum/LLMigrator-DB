package infraestructura.externalAPIs.rabbitMQ.dto.out;

public class EspacioModificacion extends EventoRabbit {
	private String nombre;
	private String descripcion;
	private int capacidad;

	public EspacioModificacion() {
		super(TipoEvento.ESPACIO_MODIFICADO, null);
	}

	public EspacioModificacion(String entidadId, String nombre, String descripcion, int capacidad) {
		super(TipoEvento.ESPACIO_MODIFICADO, entidadId);
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public int getCapacidad() {
		return capacidad;
	}

}
