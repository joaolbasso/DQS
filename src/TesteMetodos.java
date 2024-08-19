
import DAO.CidadeDAO;
import Model.Cidade;
import Model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteMetodos {
    public static void main(String[] args) {
        /*
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("DQSPU");
        EntityManager gerente = fabrica.createEntityManager();
        
        CidadeDAO cidadaDAO = new CidadeDAO();
        Cidade cidade = cidadaDAO.buscaCidadePorId(348);
        
        gerente.getTransaction().begin();
        Cidade cidade2 = gerente.find(Cidade.class, 348);
        gerente.getTransaction().commit();
        
        System.out.println(cidade.getNome_cidade());
        System.out.println(cidade2.getNome_cidade());
        */
        
        CidadeDAO cidadeDAO = new CidadeDAO();
        List<Cidade> res = cidadeDAO.todasAsCidades(1);
        
        for (Cidade cidade : res) {
            System.out.println(cidade.getNome_cidade());
        }

        
        
        
    }
}
