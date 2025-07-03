package infraestructura.repositorio;

import dominio.EspacioFisico;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import infraestructura.repositorio.excepciones.RepositorioException;
import utils.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RepositorioEspacioFisicoAdhocJPA extends RepositorioEspaciosFisicosJPA
		implements RepositorioEspacioFisicoAdhoc {

	@Override
	public List<EspacioFisico> getEspaciosFisicosByPropietario(String propietario) throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT e " + " FROM EspacioFisico e " + "WHERE e.propietario = :propietario ";

		Query query = em.createQuery(queryString);
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		query.setParameter("propietario", propietario);

		return query.getResultList();
	}

	@Override
	public List<EspacioFisico> getEspaciosFisicosByIds(List<UUID> ids) throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT e " + "FROM EspacioFisico e " + "WHERE e.id IN :ids";

		Query query = em.createQuery(queryString);
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		query.setParameter("ids", ids);

		return query.getResultList();
	}

}
