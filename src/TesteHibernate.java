
import Model.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteHibernate {
    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("DQSPU");
        EntityManager gerente = fabrica.createEntityManager();
        
        Usuario joao = new Usuario("joaolbasso", "joao12345");
        
        gerente.getTransaction().begin();
        gerente.persist(joao);
        gerente.getTransaction().commit();
        
    }
}
