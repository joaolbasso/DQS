package DAO;

import Model.Cidade;
import Model.Estado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class CidadeDAO extends AbstractDAO {
    
    public Cidade buscaCidadePorId(int id_cidade) {
        Cidade cidade = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            cidade = em.find(Cidade.class, id_cidade);
        }   
        finally {
            if (em != null) {
            em.close();
            }
    }
    return cidade;
}
    
    public List<Cidade> todasAsCidades(int estadoId) {
        List<Cidade> cidades = new ArrayList<>();
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            
            em.getTransaction().begin();
            
            Estado estado = em.find(Estado.class, estadoId);
            if (estado != null) {
                String jpql = "SELECT c FROM Cidade c WHERE c.estado = :estado";
                TypedQuery<Cidade> query = em.createQuery(jpql, Cidade.class);
                query.setParameter("estado", estado);
                cidades = query.getResultList();
            }
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
        return cidades;
    }
}
        
        
    
    
