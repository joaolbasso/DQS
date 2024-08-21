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

    @FXML
    private Button btnRegistrarDespesa;
    
    public void voltarMenuPrincipal(ActionEvent event) throws IOException {
        Parent menuPrincipalView = FXMLLoader.load(getClass().getResource("/View/MenuPrincipal.fxml"));
        Scene MenuPrincipalScene = new Scene(menuPrincipalView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(MenuPrincipalScene);
        window.show();
    }
    
    public void entrarRegistrarDespesa(ActionEvent event) throws IOException {
        Parent caixaView = FXMLLoader.load(getClass().getResource("/View/RegistrarDespesa.fxml"));
        Scene caixaScene = new Scene(caixaView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
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