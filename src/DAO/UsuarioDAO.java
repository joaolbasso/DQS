package DAO;

import Model.Cidade;
import Model.Usuario;

public class UsuarioDAO extends AbstractDAO {
    
    public void insert(Usuario usuario) {
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
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
    
    public Usuario selectUnico() {
        Usuario usuario = null;
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            usuario = em.find(Usuario.class, 5);
        }   
        finally {
            if (em != null) {
            em.close();
            }
        }
    return usuario;
    }
}
