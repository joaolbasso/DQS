
import DAO.BeneficiarioDAO;
import DAO.CaixaDAO;
import DAO.CidadeDAO;
import DAO.ClienteDAO;
import DAO.Item_caixaDAO;
import Model.Beneficiario;
import Model.Caixa;
import Model.Cidade;
import Model.Cliente;
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
        CaixaDAO caixaDAO = new CaixaDAO();
        Caixa caixa = caixaDAO.buscarCaixaAberto();
        
        Item_caixaDAO item_caixaDAO = new Item_caixaDAO();
        ObservableList<Item_caixa> itens_caixa = FXCollections.observableArrayList(item_caixaDAO.todosOsItens_Caixa(caixa));
        
        for (Item_caixa item_caixa : itens_caixa) {
            System.out.println(item_caixa.getValor_item_caixa());
        }
               
    }
}
