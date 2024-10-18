
import DAO.BeneficiarioDAO;
import DAO.CaixaDAO;
import DAO.CidadeDAO;
import DAO.ClienteDAO;
import DAO.Item_caixaDAO;
import Model.Beneficiario;
import Model.Caixa;
import Model.Cidade;
import Model.Cliente;
import Model.Despesa;
import Model.Item_caixa;
import Model.Item_venda;
import Model.Venda;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteMetodos {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        
        ClienteDAO clienteDAO = new ClienteDAO();
        
        System.out.println(clienteDAO.clientePorNomeTemParcelasEmAberto("Ana Clara Silva"));
        System.out.println(clienteDAO.clientePorNomeTemParcelasEmAberto("João Leonardo Basso"));
        
        
    }
}
