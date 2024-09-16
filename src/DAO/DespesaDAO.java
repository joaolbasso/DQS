package DAO;

import Model.Despesa;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class DespesaDAO extends AbstractDAO {

    public void insert(Despesa despesa) {
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(despesa);
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

    public List<Despesa> todasAsDespesas() {
        List<Despesa> todasAsDespesas = new ArrayList<>();
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            String jpql = "SELECT d FROM Despesa d";
            TypedQuery<Despesa> query = em.createQuery(jpql, Despesa.class);
            todasAsDespesas = query.getResultList();
            
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
        return todasAsDespesas != null ? todasAsDespesas : new ArrayList<>();
    }
    
    public void update(Despesa despesa) {
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            // Atualizar a despesa existente
            em.merge(despesa);
            
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

    public void delete(Despesa despesa) {
        EntityManager em = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            // Remover a despesa
            despesa = em.find(Despesa.class, despesa.getId_despesa());
            if (despesa != null) {
                em.remove(despesa);
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
    
    public List<Despesa> buscarDespesasNaoPagas() {
        EntityManager em = null;
        List<Despesa> despesasNaoPagas = new ArrayList<>();
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            
            LocalDate dataLimite = LocalDate.now().plusDays(10);
            String jpql = "SELECT d FROM Despesa d WHERE d.data_pagamento_despesa IS NULL AND d.data_vencimento_despesa <= :dataLimite";
            
            TypedQuery<Despesa> query = em.createQuery(jpql, Despesa.class);
            query.setParameter("dataLimite", dataLimite);
            despesasNaoPagas = query.getResultList();
            
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
        return despesasNaoPagas != null ? despesasNaoPagas : new ArrayList<>();
    }
    
}
