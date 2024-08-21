import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
