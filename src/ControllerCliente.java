/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import DAO.ClienteDAO;
import Model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerCliente implements Initializable {

    
    @FXML
    private Button btnRegistrarCliente;
    
    //@FXML
    //private TableColumn<Cliente, String> id_cliente = new TableColumn<>("Id");
    
    @FXML
    private TableColumn<Cliente, String> nome_cliente = new TableColumn<>("Nome");
    
    @FXML
    private TableColumn<Cliente, String> cpf = new TableColumn<>("CPF");
    
    @FXML
    private TableColumn<Cliente, String> telefone = new TableColumn<>("Telefone");
    
    @FXML
    private TableView<Cliente> tbvwClientes;
    
    @FXML
    public void voltarMenuPrincipal(ActionEvent event) throws IOException {
        Parent caixaView = FXMLLoader.load(getClass().getResource("/View/MenuPrincipal.fxml"));
        Scene caixaScene = new Scene(caixaView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
    }
    
    @FXML
    public void entrarCadastrarCliente(ActionEvent event) throws IOException {
        Parent cadastrarClienteView = FXMLLoader.load(getClass().getResource("/View/CadastrarCliente.fxml"));
        Scene cadastrarClienteScene = new Scene(cadastrarClienteView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(cadastrarClienteScene);
        window.show();
    }
    
    ClienteDAO clienteDAO = new ClienteDAO();
    ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    
    private <T> void centralizarTextoNaColuna(TableColumn<T, String> coluna) {
    coluna.setCellFactory(column -> {
        return new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        };
    });
}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            //id_cliente.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));
            nome_cliente.setCellValueFactory(new PropertyValueFactory<>("nome_cliente"));
            cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
            
            //centralizarTextoNaColuna(id_cliente);
            centralizarTextoNaColuna(nome_cliente);
            centralizarTextoNaColuna(cpf);
            centralizarTextoNaColuna(telefone);
            
            tbvwClientes.setItems(clientes);
    }    
    
}
