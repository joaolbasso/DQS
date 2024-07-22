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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerCadastrarItem implements Initializable {

    
    @FXML
    private TextField txtfldNomeItem;
    
    @FXML
    private TextField txtfldPrecoCusto;
    
    @FXML
    private TextField txtfldPrecoVenda;
    
    @FXML
    private DatePicker dtpckrDataCompra;
    
    @FXML
    private Spinner spnrQuantidade;
    
    @FXML
    private TextField txtfldDescricao;
    
    public void voltarParaItens(ActionEvent event) throws IOException {
        Parent itemView = FXMLLoader.load(getClass().getResource("/View/Item.fxml"));
        Scene itemScene = new Scene(itemView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(itemScene);
        window.show();
    }
    
    public void cadastrarItem(ActionEvent event) throws IOException {
        System.out.println("CADASTRADO");
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        System.out.println("LIMPOU");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
