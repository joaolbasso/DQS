package DAO;

import Model.Caixa;
import Model.Caixa.StatusCaixa;
import Model.Cidade;
import Model.Cliente;
import Model.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CaixaDAO extends AbstractDAO {
    
    public Caixa buscarCaixaAberto() {
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            String jpql = "SELECT c FROM Caixa c WHERE c.estado_caixa = :status";
            TypedQuery<Caixa> query = em.createQuery(jpql, Caixa.class);
            query.setParameter("status", StatusCaixa.ABERTO);

            List<Caixa> resultados = query.getResultList();
            em.getTransaction().commit();
            // Verifica se encontrou um caixa com status ABERTO
            if (!resultados.isEmpty()) {
        // Retorna o primeiro caixa encontrado com status ABERTO
                return resultados.get(0);
            }
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
        return null;
}
    
    public void insert(Caixa caixa) {
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(caixa);
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
    
    public void update(Caixa caixa) {
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.merge(caixa);
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
    
    public void fecharCaixa(Caixa caixa) {
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.merge(caixa);
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
    
    
    
    public Caixa buscaCaixaPorId(int id_caixa) {
        Caixa caixa = null;
        Caixa caixaNovo = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            caixa = em.find(Caixa.class, id_caixa);
            caixaNovo = (Caixa) em.merge(caixa);
        }   
        finally {
            if (em != null) {
            em.close();
            }
        }
    return caixaNovo;
}
    
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void select() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
