package infraestructura.externalAPIs.eventosAPI;

import infraestructura.repositorio.excepciones.EntidadNoEncontrada;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EventosAPI {
    List<UUID> getEspaciosSinEventosYCapacidadSuficiente(int capacidad, String fechaInicio, String fechaFin) throws IOException;
    boolean isOcupacionActiva(UUID id) throws IOException, EntidadNoEncontrada;
    boolean validarNuevaCapacidadEspacio(UUID idEspacio, int nuevaCapacidad) throws IOException, EntidadNoEncontrada;
}
