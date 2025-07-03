package infraestructura.api.rest.DTO;

import java.util.List;

import dominio.PuntoInteres;

public class ListaPuntosInteresDTO {
	
	private List<PuntoInteres> puntos;
	
	public ListaPuntosInteresDTO(List<PuntoInteres> puntos) {
		this.puntos = puntos;
	}
	
	public List<PuntoInteres> getPuntos() {
		return this.puntos;
	}
	
	public void setPuntosInteres(List<PuntoInteres> puntos) {
		this.puntos = puntos;
	}
	
	
	
	

}
