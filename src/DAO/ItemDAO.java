package DAO;

import Model.Cliente;
import Model.Item;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ItemDAO extends AbstractDAO {
    
    
    public void insert(Item item) {
        try {
            emf = EntityManagerFactorySingleton.getInstance();
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Item> todosOsItens() {
        List<Item> todosOsItens = new ArrayList<>();
        
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            
            em.getTransaction().begin();
            
                String jpql = "SELECT i FROM Item i";
                TypedQuery<Item> query = em.createQuery(jpql, Item.class);
                todosOsItens = query.getResultList();
                
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
        
        if (todosOsItens != null) {
            return todosOsItens;
        }
        List<Item> itensVazio = new ArrayList<>();
        return itensVazio;
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
