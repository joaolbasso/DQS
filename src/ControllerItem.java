import DAO.ItemDAO;
import Model.Item;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.PropertyValueException;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerItem implements Initializable {

    private Scene cenaAnterior;

    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
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
    private Button btnRegistrarItem;
    
    @FXML
    private TableView<Item> tbvwItens;
    
    @FXML
    private TableColumn<Item, Integer> id_item = new TableColumn<>("Id");
    
    @FXML
    private TableColumn<Item, String> nome_item = new TableColumn<>("Nome");
    
    @FXML
    private TableColumn<Item, Double> valor_item = new TableColumn<>("Valor");
    
    @FXML
    private TableColumn<Item, Integer> quantidade = new TableColumn<>("Quantidade");
    
    @FXML
    private TableColumn<Item, Character> tipo_item = new TableColumn<>("Tipo");
    
    public void voltarMenuPrincipal(ActionEvent event) throws IOException {
        
        Parent caixaView = FXMLLoader.load(getClass().getResource("/View/MenuPrincipal.fxml"));
        Scene caixaScene = new Scene(caixaView);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
    }
    
    ItemDAO itemDAO = new ItemDAO();
    ObservableList<Item> itens = FXCollections.observableArrayList(itemDAO.todosOsItens());
    

    public void entrarCadastrarItem(ActionEvent event) throws IOException {
        // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarItem.fxml"));
        Parent cadastrarItemView = loader.load();

        // Obter o controlador da nova tela
        ControllerCadastrarItem controllerCadastrarItem = loader.getController();

        // Definir a cena atual como a anterior no controlador da nova tela
        controllerCadastrarItem.setCenaAnterior(((Node) event.getSource()).getScene());

        // Mudar para a nova cena
        Scene cadastrarItemScene = new Scene(cadastrarItemView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cadastrarItemScene);
        window.show();
    }

    
    private <T, U> void centralizarTextoNaColuna(TableColumn<T, U> coluna) {
    coluna.setCellFactory(column -> new TableCell<T, U>() {
        @Override
        protected void updateItem(U item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                setText(item.toString());
                setStyle("-fx-alignment: CENTER;");
            }
        }
    });
}
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_item.setCellValueFactory(new PropertyValueFactory<>("id_item"));
        nome_item.setCellValueFactory(new PropertyValueFactory<>("nome_item"));
        valor_item.setCellValueFactory(new PropertyValueFactory<>("valor_item"));
        quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tipo_item.setCellValueFactory(new PropertyValueFactory<>("tipo_item"));
        
        centralizarTextoNaColuna(id_item);
        centralizarTextoNaColuna(nome_item);
        centralizarTextoNaColuna(valor_item);
        centralizarTextoNaColuna(quantidade);
        centralizarTextoNaColuna(tipo_item);
        
        tbvwItens.setItems(itens);
    }    
    
}
