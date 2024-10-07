package DAO;

import Model.Pagamento;
import java.util.List;
import javax.persistence.Query;

public class PagamentoDAO extends AbstractDAO {

    public void insert(Pagamento pagamento) {
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(pagamento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagamento> listarPagamentosPorParcela(int id_parcela) {
        List<Pagamento> pagamentos = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            String jpql = "SELECT p FROM Pagamento p WHERE p.parcela.id_parcela = :id_parcela";
            Query query = em.createQuery(jpql);
            query.setParameter("id_parcela", id_parcela);
            
            pagamentos = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return pagamentos;
    }
}
