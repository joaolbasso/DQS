package DAO;

import Model.Estado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class EstadoDAO extends AbstractDAO {

    public Estado buscaEstadoPorId(int id_estado) {
        Estado estado = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            estado = em.find(Estado.class, id_estado);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return estado;
    }

    public List<Estado> todosOsEstados() {
        List<Estado> estados = new ArrayList<>();
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();

            em.getTransaction().begin();

            String jpql = "SELECT e FROM Estado e";
            TypedQuery<Estado> query = em.createQuery(jpql, Estado.class);
            estados = query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return estados;
    }
}
