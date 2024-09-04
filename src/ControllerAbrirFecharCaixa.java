import DAO.CaixaDAO;
import DAO.Item_caixaDAO;
import DAO.UsuarioDAO;
import Model.Caixa;
import Model.Item_caixa;
import Model.Usuario;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.TypedQuery;
import javax.swing.Action;
import javax.swing.JOptionPane;

public class ControllerAbrirFecharCaixa implements Initializable {
    
    private Caixa caixa;

    @FXML
    private Label lblTitulo;
    
    @FXML
    private TableView<Item_caixa> tbvwItensCaixa;
    
    @FXML
    private Button btnAbrirCaixa;
    
    @FXML
    private Button btnFecharCaixa;
    
    @FXML
    private TableColumn<Item_caixa, String> tbclnDescricao = new TableColumn<>("Descrição do Item");
    @FXML
    private TableColumn<Item_caixa, Double> tbclnValor = new TableColumn<>("Valor do Item");
    @FXML
    private TableColumn<Item_caixa, LocalDateTime> tbclnData = new TableColumn<>("Data do Item");
    
    Item_caixaDAO item_caixaDAO = new Item_caixaDAO();
    private ObservableList<Item_caixa> itens_caixa = FXCollections.observableArrayList();
    
    
    
    @FXML
    public void abrirCaixa(ActionEvent event) throws IOException {
        Usuario usuario = usuarioDAO.selectUnico(); //Por enquanto busca um unico usuario na base, com id 5
        
        Caixa caixaNovo = new Caixa(usuario); // Usuario Logado
        caixaDAO.insert(caixaNovo);
        JOptionPane.showMessageDialog(null, "Caixa aberto com sucesso!", "Caixa aberto com sucesso!", JOptionPane.INFORMATION_MESSAGE);
        
        voltar(event);
    }
    
    @FXML
    public void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuPrincipal.fxml"));
        Parent itemView = loader.load();
        Scene itemScene = new Scene(itemView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(itemScene);
        window.show();
    }
    
    @FXML
    public void fecharCaixa(ActionEvent event) throws IOException {
        Caixa caixaFechar = getCaixa();
        
        caixaFechar.setData_hora_fechamento(LocalDateTime.now());
        caixaFechar.setEstado_caixa(Caixa.StatusCaixa.FECHADO);
        
        caixaDAO.fecharCaixa(caixaFechar);
        JOptionPane.showMessageDialog(null, "Caixa fechado com sucesso!", "Caixa fechado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
        
        voltar(event);
        
    }
    
    private CaixaDAO caixaDAO = new CaixaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.caixa = caixaDAO.buscarCaixaAberto();
        
        if (this.caixa == null) {
            btnAbrirCaixa.setDisable(false);
            btnFecharCaixa.setDisable(true);
            lblTitulo.setText("Não há um caixa aberto!");
        } else {
            btnAbrirCaixa.setDisable(true);
            btnFecharCaixa.setDisable(false);
            lblTitulo.setText("Há um caixa aberto!");
            
            itens_caixa.addAll(item_caixaDAO.todosOsItens_Caixa(this.caixa));
            tbvwItensCaixa.setItems(itens_caixa);
            tbclnData.setCellValueFactory(new PropertyValueFactory<>("data_hora"));
            tbclnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao_item_caixa"));
            tbclnValor.setCellValueFactory(new PropertyValueFactory<>("valor_item_caixa"));
            
            //Trazer itens caixa para a TableView
            
        }
    }

     public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public ObservableList<Item_caixa> getItens_caixa() {
        return itens_caixa;
    }

    public void setItens_caixa(ObservableList<Item_caixa> itens_caixa) {
        this.itens_caixa = itens_caixa;
    }
    
    
}
