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
    
    // Método para atualizar um item
    public void update(Item item) {
        try {
            emf = EntityManagerFactorySingleton.getInstance();
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            // Atualizar o item no banco de dados
            em.merge(item);
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Método para deletar um item
    public void delete(Item item) {
        try {
            emf = EntityManagerFactorySingleton.getInstance();
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            // Buscar o item no banco de dados
            Item itemToDelete = em.find(Item.class, item.getId_item());
            if (itemToDelete != null) {
                // Remover o item
                em.remove(itemToDelete);
            }
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
