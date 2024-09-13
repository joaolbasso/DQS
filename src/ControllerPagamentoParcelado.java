import DAO.ClienteDAO;
import Model.Cliente;
import Model.Parcela;
import Model.Venda;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerPagamentoParcelado implements Initializable {

    private Scene cenaAnterior;
    private ItemCallback itemCallBack;
    
    @FXML
    private Label lblValorParcela;
    
    @FXML
    private Label lblNumeroParcela;
    
    @FXML
    private ComboBox<String> cmbboxMetodoPagamento;
    
    ObservableList<String> metodos_pagamento = FXCollections.observableArrayList("Espécie", "PIX", "Débito", "Crédito");
    
    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
    if (itemCallBack != null) {
            itemCallBack.onItemUpdated();
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(this.cenaAnterior);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbboxMetodoPagamento.setItems(metodos_pagamento);
    }    
    
    @FXML
    public void quitarParcela(ActionEvent event) throws IOException {
        System.out.println("QUITAR");
    }
    
    @FXML
    public void limparCampos(ActionEvent event) throws IOException {
        System.out.println("LIMPAR CAMPOS");
    }
    public interface ItemCallback {
        void onItemUpdated();
    }

    public void setItemCallBack(ItemCallback itemCallBack) {
        this.itemCallBack = itemCallBack;
    }
    
    
}
