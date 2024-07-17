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
        Parent caixaView = FXMLLoader.load(getClass().getResource("/View/Caixa.fxml"));
        Scene caixaScene = new Scene(caixaView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
    }
    
    @FXML
    public void entrarDespesas(ActionEvent event) throws IOException {
        Parent despesaView = FXMLLoader.load(getClass().getResource("/View/Despesa.fxml"));
        Scene despesaScene = new Scene(despesaView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(despesaScene);
        window.show();
    }
    
    @FXML
    public void entrarClientes(ActionEvent event) throws IOException {
        Parent clienteView = FXMLLoader.load(getClass().getResource("/View/Cliente.fxml"));
        Scene clienteScene = new Scene(clienteView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(clienteScene);
        window.show();
    }
    
    @FXML
    public void entrarItens(ActionEvent event) throws IOException {
        Parent itemView = FXMLLoader.load(getClass().getResource("/View/Item.fxml"));
        Scene itemScene = new Scene(itemView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
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
