package infraestructura.externalAPIs.rabbitMQ.excepciones;

@SuppressWarnings("serial")
public class BusEventosException extends Exception {
	public BusEventosException(String mensaje) {
		super(mensaje);
	}

	public BusEventosException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}
