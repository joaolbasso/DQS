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

    
    @FXML
    private Button btnRegistrarItem;
    
    public void voltarMenuPrincipal(ActionEvent event) throws IOException {
        
        Parent caixaView = FXMLLoader.load(getClass().getResource("/View/MenuPrincipal.fxml"));
        Scene caixaScene = new Scene(caixaView);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
    }
    
    public void entrarCadastrarItem(ActionEvent event) throws IOException {
        Parent cadastrarItemView = FXMLLoader.load(getClass().getResource("/View/CadastrarItem.fxml"));
        Scene cadastrarItemScene = new Scene(cadastrarItemView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
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
