package infraestructura.api.rest.mapper;

import dominio.EspacioFisico;
import infraestructura.api.rest.DTO.EspacioFisicoDTO;

public class EspacioFisicoMapper {

  // EspacioFisico -> EspacioFisicoDTO
  public static EspacioFisicoDTO transformToEspacioFisicoDTO(EspacioFisico espacioFisico) {
    EspacioFisicoDTO espacioFisicoDto = new EspacioFisicoDTO();
    espacioFisicoDto.setId(espacioFisico.getId());
    espacioFisicoDto.setNombre(espacioFisico.getNombre());
    espacioFisicoDto.setCapacidad(espacioFisico.getCapacidad());
    espacioFisicoDto.setDescripcion(espacioFisico.getDescripcion());
    espacioFisicoDto.setDireccion(espacioFisico.getDireccion());
    espacioFisicoDto.setEstado(espacioFisico.getEstado());
    return espacioFisicoDto;
  }
}
