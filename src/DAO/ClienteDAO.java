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
            
            String jpql = "SELECT c FROM Cliente c ORDER BY c.nome_cliente ASC";
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
    
    public List<Cliente> buscarClientesLazy(String filtro) {
    EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
    
    List<Cliente> clientes = new ArrayList<>();
    
    try {
        // Consulta dinâmica com parâmetro LIKE para buscar clientes
        String query = "SELECT c FROM Cliente c WHERE LOWER(c.nome_cliente) LIKE LOWER(:nome)";
        clientes = em.createQuery(query, Cliente.class)
                     .setParameter("nome", "%" + filtro + "%") // Filtra pelo nome
                     .setMaxResults(10) // Limita o número de resultados retornados
                     .getResultList();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        em.close(); // Fecha o EntityManager após a consulta
    }
    
    return clientes;
}
    
   public Cliente getClientePorNome(String filtro) {
       //Utilizar função quando ter certeza que o cliente existe
    EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
    
    Cliente cliente = new Cliente();
    
    try {
        // Consulta dinâmica com parâmetro LIKE para buscar clientes
        String query = "SELECT c FROM Cliente c WHERE LOWER(c.nome_cliente) LIKE LOWER(:nome)";
        cliente = em.createQuery(query, Cliente.class)
                     .setParameter("nome", "%" + filtro + "%") // Filtra pelo nome
                     .setMaxResults(1) // Limita o número de resultados retornados
                     .getSingleResult();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        em.close(); // Fecha o EntityManager após a consulta
    }
    
    return cliente;
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
    
    public boolean clientePorNomeTemParcelasEmAberto(String nome_cliente) {
    boolean resultado = false;
    EntityManager em = null;

    try {
        em = EntityManagerFactorySingleton.getInstance().createEntityManager();
        em.getTransaction().begin();
        
        // JPQL para contar o número de parcelas em aberto para o cliente
        String jpql = "SELECT COUNT(p) FROM Parcela p " +
                      "INNER JOIN p.venda v " +
                      "INNER JOIN v.cliente c " +
                      "WHERE p.condicao = 'pendente' AND c.nome_cliente = :nome_cliente";
                      
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("nome_cliente", nome_cliente);
        
        Long count = query.getSingleResult();  // Retorna a contagem de parcelas pendentes
        
        // Se count for maior que 0, o cliente tem parcelas em aberto
        resultado = (count > 0);
        
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
    return resultado;
}

    
}
