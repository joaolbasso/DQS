package DAO;

import Model.Venda;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class VendaDAO extends AbstractDAO {

    public void insert(Venda venda) {
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(venda);
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

    public List<Venda> listaVendasPorCliente(int id_cliente) {
        EntityManager em = null;
        List<Venda> vendas = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            TypedQuery<Venda> query = em.createQuery("SELECT v FROM Venda v WHERE v.cliente.id_cliente = :id_cliente", Venda.class);
            query.setParameter("id_cliente", id_cliente);
            vendas = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return vendas;
    }

}
