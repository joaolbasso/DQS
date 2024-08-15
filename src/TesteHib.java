import Model.Estado;
import Model.Usuario;
import Model.Cidade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TesteHib {
    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("DQSPU");
        EntityManager gerente = fabrica.createEntityManager();
        
        
        Usuario luiz = new Usuario("LUIZ89", "Cebola");
        
        gerente.getTransaction().begin();
        gerente.persist(luiz);
        gerente.getTransaction().commit();
        
        /*
        Exemplo pegando do BANCO
        Usuario doBanco = null;
        gerente.getTransaction().begin();
        doBanco = (Usuario) gerente.find(Usuario.class, 1);
        gerente.getTransaction().commit();
        
        System.out.println(doBanco.getUsuario());
        System.out.println(doBanco.getSenha());
        */
        
        gerente.close();
        
        
        
        fabrica.close();
        
        
    }
}