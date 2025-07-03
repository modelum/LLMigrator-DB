package infraestructura.externalAPIs.rabbitMQ;

import dominio.EspacioFisico;
import infraestructura.externalAPIs.rabbitMQ.excepciones.BusEventosException;

public interface PublicadorEspacios {
	void publicarEspacioCreacion(EspacioFisico evento) throws BusEventosException;
	void publicarEspacioModificacion(EspacioFisico evento) throws BusEventosException;
	void publicarEspacioBorrado(String entidadId) throws BusEventosException;
	void publicarEspacioActivado(String entidadId) throws BusEventosException;
}
