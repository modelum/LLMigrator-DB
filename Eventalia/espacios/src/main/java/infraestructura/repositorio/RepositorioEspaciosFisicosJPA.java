package infraestructura.repositorio;

import dominio.EspacioFisico;

public class RepositorioEspaciosFisicosJPA extends RepositorioJPA<EspacioFisico> {

  @Override
  public Class<EspacioFisico> getClase() {
    return EspacioFisico.class;
  }
}
