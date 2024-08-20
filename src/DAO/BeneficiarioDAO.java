package DAO;

import Model.Beneficiario;
import Model.Cidade;
import Model.Estado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class BeneficiarioDAO extends AbstractDAO {


    public void insert(Beneficiario beneficiario) {
        
        try {
            emf = EntityManagerFactorySingleton.getInstance();
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(beneficiario);
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

    public List<Beneficiario> todosOsBeneficiarios() {
        List<Beneficiario> beneficiarios = new ArrayList<>();
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            
            em.getTransaction().begin();
            
                String jpql = "SELECT b FROM Beneficiario b";
                TypedQuery<Beneficiario> query = em.createQuery(jpql, Beneficiario.class);
                beneficiarios = query.getResultList();
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
        if (beneficiarios.isEmpty()) {
            List<Beneficiario> beneficiariosVazio = new ArrayList<>();
            return beneficiariosVazio;
        } else {
            return beneficiarios;
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
