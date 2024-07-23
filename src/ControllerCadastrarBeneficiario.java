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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerCadastrarBeneficiario implements Initializable {

    @FXML
    private TextField txtfldNomeBeneficiario;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void registrarDespesa(ActionEvent event) {
        System.out.println(txtfldNomeBeneficiario.getText());
    }
    
    public void limparCampos(ActionEvent event) {
        txtfldNomeBeneficiario.setText("");
    }
    public void voltarParaDespesa(ActionEvent event) throws IOException {
        Parent registrarDespesaView = FXMLLoader.load(getClass().getResource("/View/RegistrarDespesa.fxml"));
        Scene registrarDespesaScene = new Scene(registrarDespesaView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(registrarDespesaScene);
        window.show();
    }
    
    
}
