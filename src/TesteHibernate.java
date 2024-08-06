import Model.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteHibernate {
    public static void main(String[] args) {
        EntityManagerFactory fabrica = null;
        EntityManager gerente = null;

        try {
            fabrica = Persistence.createEntityManagerFactory("DQSPU");
            gerente = fabrica.createEntityManager();
            
            Usuario l = new Usuario("joao", "olimpiadas");
            
            gerente.detach(l);
            gerente.getTransaction().begin();
            gerente.persist(l);
            gerente.getTransaction().commit();
        } catch (Exception e) {
            if (gerente != null && gerente.getTransaction().isActive()) {
                gerente.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (gerente != null) {
                gerente.close();
            }
            if (fabrica != null) {
                fabrica.close();
            }
        }
    }
}
