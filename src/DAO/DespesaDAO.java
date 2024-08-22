package DAO;

import Model.Despesa;
import Model.Item;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;

public class DespesaDAO extends AbstractDAO {

    public void insert(Despesa despesa) {
        
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(despesa);
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

    public List<Despesa> todasAsDespesas() {
        List<Despesa> todasAsDespesas = new ArrayList<>();
        
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            
            em.getTransaction().begin();
            
                String jpql = "SELECT d FROM Despesa d";
                TypedQuery<Despesa> query = em.createQuery(jpql, Despesa.class);
                todasAsDespesas = query.getResultList();
                
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
        if (todasAsDespesas != null) {
            return todasAsDespesas;
        }
        List<Despesa> despesasVazia = new ArrayList<>();
            return despesasVazia;
        
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
