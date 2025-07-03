package infraestructura.externalAPIs.rabbitMQ.dto.out;

public class EspacioActivado extends EventoRabbit {

	public EspacioActivado() {
		super(TipoEvento.ESPACIO_ACTIVADO, null);
	}

	public EspacioActivado(String EntidadId) {
		super(TipoEvento.ESPACIO_ACTIVADO, EntidadId);
	}
}
