
import DAO.BeneficiarioDAO;
import DAO.CidadeDAO;
import DAO.ClienteDAO;
import Model.Beneficiario;
import Model.Cidade;
import Model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteMetodos {
    public static void main(String[] args) {
        
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("DQSPU");
        EntityManager gerente = fabrica.createEntityManager();
        /*
        CidadeDAO cidadaDAO = new CidadeDAO();
        Cidade cidade = cidadaDAO.buscaCidadePorId(348);
        
        gerente.getTransaction().begin();
        Cidade cidade2 = gerente.find(Cidade.class, 348);
        gerente.getTransaction().commit();
        
        System.out.println(cidade.getNome_cidade());
        System.out.println(cidade2.getNome_cidade());
        */
        /*
        CidadeDAO cidadeDAO = new CidadeDAO();
        List<Cidade> res = cidadeDAO.todasAsCidades(1);
        
        for (Cidade cidade : res) {
            System.out.println(cidade.getNome_cidade());
        }
*/
        /*
        BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
        List<Beneficiario> beneL = beneficiarioDAO.todosOsBeneficiarios();
        for (Beneficiario beneficiario : beneL) {
            System.out.println(beneficiario.getNome_beneficiario());
        }
        */
        
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clientes = clienteDAO.todosOsClientes();
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getNome_cliente());
        }
        
        gerente.close();
        fabrica.close();
               
    }
}