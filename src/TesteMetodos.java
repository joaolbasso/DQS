
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

       String cpfValido = "01234565748";
       String cpfFalso = "11111111111";
       
       System.out.println(Config.ValidaCPF.isCPF(cpfValido));
        System.out.println(Config.ValidaCPF.isCPF(cpfFalso));
        
        
        Cliente cliente = new Cliente();
        
        ClienteDAO clienteDAO = new ClienteDAO();
        
        cliente = clienteDAO.getCliente(1);
        
        System.out.println(cliente.getNome_cliente());
        
        CaixaDAO caixaDAO = new CaixaDAO();
        
        Caixa caixa = caixaDAO.buscarCaixaAberto();
        
        System.out.println(caixaDAO.entradasCaixa(caixa));
    
        Beneficiario bene = new Beneficiario("COPEL");
        
        Item_caixa item_caixa = new Item_caixa(0.0, LocalDate.now(), Item_caixa.TipoOperacao.D, caixaDAO.buscarCaixaAberto(), 'D', "Despesa");
        Despesa despesa = new Despesa("Luz", 50.0, "Luz da copel", 15, LocalDate.now(), LocalDate.now().plusDays(3), bene, item_caixa);
        
        item_caixa.setValor_item_caixa(despesa.getValor_despesa());
        
        
        
    }
}
