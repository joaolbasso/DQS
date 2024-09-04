package DAO;

import Model.Caixa;
import Model.Item_caixa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;

public class Item_caixaDAO extends AbstractDAO {

    public List<Item_caixa> todosOsItens_Caixa(Caixa caixa) {
        List<Item_caixa> todosOsItens_caixa = new ArrayList<>();
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            String jpql = "SELECT c FROM Item_caixa c WHERE c.caixa = :caixa";
            TypedQuery<Item_caixa> query = em.createQuery(jpql, Item_caixa.class);
            query.setParameter("caixa", caixa);
            todosOsItens_caixa = query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return todosOsItens_caixa != null ? todosOsItens_caixa : new ArrayList<>();
    }
    
    public void insert(Item_caixa item_caixa) {
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(item_caixa);
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

    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void select() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
