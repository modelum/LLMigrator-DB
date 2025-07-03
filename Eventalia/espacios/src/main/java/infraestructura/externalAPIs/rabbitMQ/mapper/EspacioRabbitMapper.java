package infraestructura.externalAPIs.rabbitMQ.mapper;

import dominio.EspacioFisico;
import infraestructura.externalAPIs.rabbitMQ.dto.out.EspacioCerrado;
import infraestructura.externalAPIs.rabbitMQ.dto.out.EspacioCreacion;
import infraestructura.externalAPIs.rabbitMQ.dto.out.EspacioActivado;
import infraestructura.externalAPIs.rabbitMQ.dto.out.EspacioModificacion;
import infraestructura.externalAPIs.rabbitMQ.dto.out.EventoRabbit;

public class EspacioRabbitMapper {

	public static EventoRabbit toEspacioCreacion(EspacioFisico espacio) {
		return new EspacioCreacion(espacio.getId().toString(), espacio.getNombre(), espacio.getPropietario(),
				espacio.getCapacidad(), espacio.getDireccion(), espacio.getLongitud(), espacio.getLatitud(),
				espacio.getDescripcion());
	}

	public static EventoRabbit toEspacioModificacion(EspacioFisico evento) {
		return new EspacioModificacion(evento.getId().toString(), evento.getNombre(), evento.getDescripcion(),
				evento.getCapacidad());
	}

	public static EventoRabbit toEspacioCerrado(String entidadId) {
		return new EspacioCerrado(entidadId);
	}

	public static EventoRabbit toEspacioActivo(String entidadId) {
		return new EspacioActivado(entidadId);
	}

}
