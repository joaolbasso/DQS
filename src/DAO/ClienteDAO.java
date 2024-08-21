package DAO;

import Model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;

public class ClienteDAO extends AbstractDAO {

    public void insert(Cliente cliente) {
        try {
            emf = EntityManagerFactorySingleton.getInstance();
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(cliente);
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
    
    public List<Cliente> todosOsClientes() {
        List<Cliente> todosOsClientes = new ArrayList<>();
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            
            em.getTransaction().begin();
            
                String jpql = "SELECT c FROM Cliente c";
                TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
                todosOsClientes = query.getResultList();
                
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
        if (todosOsClientes != null) {
            return todosOsClientes;
        }
            List<Cliente> clientesVazio = new ArrayList<>();
            return clientesVazio;
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
