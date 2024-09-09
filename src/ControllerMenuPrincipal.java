import DAO.CaixaDAO;
import DAO.Item_caixaDAO;
import DAO.UsuarioDAO;
import Model.Caixa;
import Model.Item_caixa;
import Model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerMenuPrincipal implements Initializable {

    @FXML
    private Button btnCaixa;
    @FXML
    private Button btnDespesas;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnItens;
    @FXML
    private Button btnCaixaAbrir;
    
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
        updateUI();
    }
    
    @FXML
    public void fecharCaixa(ActionEvent event) throws IOException {
        Caixa caixaFechar = getCaixa();
        
        caixaFechar.setData_hora_fechamento(LocalDateTime.now());
        caixaFechar.setEstado_caixa(Caixa.StatusCaixa.FECHADO);
        
        caixaDAO.fecharCaixa(caixaFechar);
        JOptionPane.showMessageDialog(null, "Caixa fechado com sucesso!", "Caixa fechado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
        updateUI();
        
        
    }
    private CaixaDAO caixaDAO = new CaixaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @FXML
    public void entrarCaixa(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Caixa.fxml"));
        Parent caixaView = loader.load();
        
        // Obter o controlador da nova tela
        ControllerCaixa controller = loader.getController();
        
        // Passar a cena atual (Menu Principal) para o controlador da nova tela
        controller.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene caixaScene = new Scene(caixaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
    }
    
    @FXML
    public void entrarDespesas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Despesa.fxml"));
        Parent despesaView = loader.load();
        
        // Obter o controlador da nova tela
        ControllerDespesa controller = loader.getController();
        
        // Passar a cena atual (Menu Principal) para o controlador da nova tela
//        controller.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene despesaScene = new Scene(despesaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(despesaScene);
        window.show();
    }
    
    @FXML
    public void entrarClientes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Cliente.fxml"));
        Parent clienteView = loader.load();
       

        Scene clienteScene = new Scene(clienteView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(clienteScene);
        window.show();
    }
    
    @FXML
    public void entrarItens(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Item.fxml"));
        Parent itemView = loader.load();
        
        // Obter o controlador da nova tela
        ControllerItem controller = loader.getController();
        
        // Passar a cena atual (Menu Principal) para o controlador da nova tela
        //controller.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene itemScene = new Scene(itemView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(itemScene);
        window.show();
    }
    
     /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
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
        
        // TODO
        btnCaixa.getStyleClass().add("color-button");
        btnDespesas.getStyleClass().add("color-button");
        btnClientes.getStyleClass().add("color-button");
        btnItens.getStyleClass().add("color-button");
    }
    }
        
    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    private void updateUI() {
    try {
        // Obter o Stage atual
        Stage stage = (Stage) btnFecharCaixa.getScene().getWindow();
        
        // Carregar o novo arquivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuPrincipal.fxml"));
        Parent root = loader.load();
        
        // Definir a nova cena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        // Opcional: Atualizar o título da janela ou outros componentes
        stage.setTitle("DQS - Sistema de informação");
        
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}

