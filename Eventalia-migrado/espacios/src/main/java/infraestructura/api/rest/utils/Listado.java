package infraestructura.api.rest.utils;

import java.util.List;

import infraestructura.api.rest.DTO.EspacioFisicoDTO;


public class Listado {
	
	public static class ResumenExtendido {
		private String url;
		private EspacioFisicoDTO resumen;
		// Métodos get y set
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public EspacioFisicoDTO getResumen() {
			return resumen;
		}
		public void setResumen(EspacioFisicoDTO resumen) {
			this.resumen = resumen;
		}
		
		
	}

	private List<ResumenExtendido> espacio;
	// Métodos get y set

	public List<ResumenExtendido> getEspacios() {
		return espacio;
	}

	public void setEspacios(List<ResumenExtendido> espacio) {
		this.espacio = espacio;
	}
	
	
}