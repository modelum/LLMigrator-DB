package eventos.infraestructura.rabbitMQ.dto.in;

import eventos.infraestructura.rabbitMQ.dto.EventoRabbit;
import eventos.infraestructura.rabbitMQ.dto.TipoEvento;

public class EspacioActivado extends EventoRabbit {

	public EspacioActivado() {
		super(TipoEvento.ESPACIO_ACTIVADO, null);
	}

	public EspacioActivado(String EntidadId) {
		super(TipoEvento.ESPACIO_ACTIVADO, EntidadId);
	}
}
