/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


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
public class ControllerDespesa implements Initializable {

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
    private Button btnRegistrarDespesa;
    
    @FXML
    public void entrarRegistrarDespesa(ActionEvent event) throws IOException {
        // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RegistrarDespesa.fxml"));
        Parent registrarDespesaView = loader.load();

        // Obter o controller da nova tela
        ControllerRegistrarDespesa controllerRegistrarDespesa = loader.getController();

        // Definir a cena atual como a anterior no controller da nova tela
        controllerRegistrarDespesa.setCenaAnterior(((Node) event.getSource()).getScene());

        // Mudar para a nova cena
        Scene registrarDespesaScene = new Scene(registrarDespesaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registrarDespesaScene);
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