/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerMenuPrincipal implements Initializable {

    @FXML
    protected void entrarCaixa(ActionEvent event) {
        System.out.println("Clique Caixa");
    }
    
    @FXML
    protected void entrarDespesas(ActionEvent event) {
        System.out.println("Clique Despesas");
    }
    
    @FXML
    protected void entrarClientes(ActionEvent event) {
        System.out.println("Clique Clientes");
    }
    
    @FXML
    protected void entrarItens(ActionEvent event) {
        System.out.println("Clique Itens");
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
