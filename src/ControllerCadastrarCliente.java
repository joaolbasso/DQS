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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerCadastrarCliente implements Initializable {
    
    @FXML
    private TextField txtfldNome;
    
    @FXML
    private TextField txtfldCPF;
    
    @FXML
    private TextField txtfldTelefone;
    
    @FXML
    private ComboBox<String> cmbboxCidade;
    
    @FXML
    private ComboBox<String> cmbboxEstado;
    
    @FXML
    private TextField txtfldCEP;
    
    @FXML
    private TextField txtfldLogradouro;
    
    @FXML
    private TextField txtfldNumero;
    
    @FXML
    private TextField txtfldComplemento;
    
    @FXML
    private TextField txtfldBairro;
    
    public void voltarParaClientes(ActionEvent event) throws IOException {
        Parent clienteView = FXMLLoader.load(getClass().getResource("/View/Cliente.fxml"));
        Scene clienteScene = new Scene(clienteView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(clienteScene);
        window.show();
    }
    
    public void cadastrarCliente(ActionEvent event) throws IOException {
        
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
