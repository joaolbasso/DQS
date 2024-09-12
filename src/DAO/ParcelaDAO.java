package DAO;

import Model.Parcela;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ParcelaDAO extends AbstractDAO {

    public void insert(Parcela parcela) {
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(parcela);
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

    public List<Parcela> listarParcelasPorVenda(int id_venda) {
        EntityManager em = null;
        List<Parcela> parcelas = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            TypedQuery<Parcela> query = em.createQuery("SELECT p FROM Parcela p WHERE p.venda.id_venda = :id_venda", Parcela.class);
            query.setParameter("id_venda", id_venda);
            parcelas = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return parcelas;
    }

}
