package infraestructura.repositorio;

import dominio.EspacioFisico;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import infraestructura.repositorio.excepciones.RepositorioException;


public interface RepositorioEspacioFisicoAdhoc extends RepositorioUUID<EspacioFisico> {

  default List<EspacioFisico> getEspaciosFisicosByPropietario(String propietario) throws RepositorioException{
		return getAll().stream().filter(espacio -> espacio.getPropietario().equals(propietario))
				.collect(Collectors.toList());
  }
  
  default List<EspacioFisico> getEspaciosFisicosByIds(List<UUID> ids) throws RepositorioException
  {
	      return getAll().stream().filter(espacio -> ids.contains(espacio.getId()))
        .collect(Collectors.toList());
  }
  
}
