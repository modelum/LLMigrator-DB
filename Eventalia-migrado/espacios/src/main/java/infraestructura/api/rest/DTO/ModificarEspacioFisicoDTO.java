package infraestructura.api.rest.DTO;

public class ModificarEspacioFisicoDTO {

	private String nombre;
	private String descripcion;
	private int capacidad;

	public ModificarEspacioFisicoDTO(String nombre, String descripcion, int capacidad) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.capacidad = capacidad;
	}

	public ModificarEspacioFisicoDTO() {
	}

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

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

}
