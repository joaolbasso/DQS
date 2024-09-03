package DAO;

import Model.Pagamento;

public class PagamentoDAO extends AbstractDAO {

    public void insert(Pagamento pagamento) {
        try {
            em = EntityManagerFactorySingleton.getInstance().createEntityManager();
            em.getTransaction().begin();
            em.persist(pagamento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        } finally {
            if (em != null) {
                em.close();
            }
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
