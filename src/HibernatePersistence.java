import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernatePersistence {
    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("DQSPU");
        EntityManager gerente = fabrica.createEntityManager();
        
        
        try {
            gerente.getTransaction().begin();

            gerente.getTransaction().commit();
        } catch (Exception e) {
            
        } finally {
            gerente.close();
            fabrica.close();
            
        }
        
        
        
        
        
    }
}
