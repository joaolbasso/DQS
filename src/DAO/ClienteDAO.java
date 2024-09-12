package DAO;

import Model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ClienteDAO extends AbstractDAO {

    public Cliente getCliente(int id_cliente) {
        Cliente todosOsClientes = new Cliente();
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            String jpql = "SELECT c FROM Cliente c WHERE c.id_cliente = :id_cliente";
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("id_cliente", id_cliente);
            todosOsClientes = query.getSingleResult();
            
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
        return todosOsClientes;
    }
    
    public void insert(Cliente cliente) {
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(cliente);
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
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return todosOsClientes != null ? todosOsClientes : new ArrayList<>();
    }
    
    public void update(Cliente cliente) {
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            // Atualizar a cliente existente
            em.merge(cliente);
            
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

    public void delete(Cliente cliente) {
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            // Remover a cliente
            cliente = em.find(Cliente.class, cliente.getId_cliente());
            if (cliente != null) {
                em.remove(cliente);
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
    
    public boolean cpfExiste(String cpf) {
        List<Cliente> cliente = new ArrayList<>();
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            String jpql = "SELECT c FROM Cliente c WHERE c.cpf = :cpf";
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("cpf", cpf);
            cliente = query.getResultList();
            
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
        return !cliente.isEmpty();
    }
    
}
