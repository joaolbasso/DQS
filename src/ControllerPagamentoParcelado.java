import DAO.ClienteDAO;
import Model.Cliente;
import Model.Parcela;
import Model.Venda;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerPagamentoParcelado implements Initializable {

    private Scene cenaAnterior;
    private Venda venda;
    
    @FXML
    private Label lblValorRestante;
    
    @FXML
    private ComboBox<Cliente> cmbboxCliente;
    
    @FXML
    private ComboBox<Integer> cmbboxParcelas;
    
    @FXML
    private DatePicker dtpkrDataVencimento;
    
    @FXML
    private TableView<Parcela> tbvwParcelas;
    
    @FXML
    private TableColumn<Parcela, Integer> tbclnParcela;
    
    @FXML
    private TableColumn<Parcela, Double> tbclnValorParcela;
    
    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
        atualizaValorRestante();
        if (this.venda.getCliente() != null) {
            cmbboxCliente.setValue(this.venda.getCliente());
        } else {
            cmbboxCliente.setItems(clientes);
        }
    }
    
    

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        // Retornar para a cena anterior se existir
        if (cenaAnterior != null) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(cenaAnterior);
            window.show();
        }
    }
    
    @FXML
    public void cadastarCliente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarCliente.fxml"));
        Parent pagamentoView = loader.load();

        // Obter o controlador da nova tela
        ControllerCadastrarCliente controller = loader.getController();

        // Passar a cena atual para o controlador da nova tela
        controller.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene pagamentoScene = new Scene(pagamentoView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(pagamentoScene);
        window.show();
    }
    
    @FXML
    public void registrarParcelas(ActionEvent event) throws IOException {
        System.out.println("REGISTRAR");
    }
    
    @FXML
    public void limparCampos(ActionEvent event) throws IOException {
        System.out.println("LIMPAR CAMPOS");
    }
    
    ClienteDAO clienteDAO = new ClienteDAO();
    ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbboxCliente.setCellFactory(cell -> new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente cliente, boolean empty) {
                super.updateItem(cliente, empty);
                if (empty || cliente == null) {
                    setText(null);
                } else {
                    setText(cliente.getNome_cliente());
                }
            }
        });
        
        cmbboxCliente.setButtonCell(cmbboxCliente.getCellFactory().call(null));
    }    

    private void atualizaValorRestante() {
        String valorRestante = Double.toString(this.venda.getValor_venda() - 10.0);
        lblValorRestante.setText(valorRestante);
    }
}
