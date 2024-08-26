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
        
        // Obter o controlador da nova tela
        ControllerCliente controller = loader.getController();
        
        // Passar a cena atual (Menu Principal) para o controlador da nova tela
        controller.setCenaAnterior(((Node) event.getSource()).getScene());

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
//        controller.setCenaAnterior(((Node) event.getSource()).getScene());

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
        // TODO
        btnCaixa.getStyleClass().add("color-button");
        btnDespesas.getStyleClass().add("color-button");
        btnClientes.getStyleClass().add("color-button");
        btnItens.getStyleClass().add("color-button");
    }    
    
}
