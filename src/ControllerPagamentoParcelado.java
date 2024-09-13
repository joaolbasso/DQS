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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerPagamentoParcelado implements Initializable {

    private Scene cenaAnterior;
    private ItemCallback itemCallBack;
    private Parcela parcela;
    
    @FXML
    private Label lblValorParcela;
    
    @FXML
    private Label lblNumeroParcela;
    
    @FXML
    private ComboBox<String> cmbboxMetodoPagamento;
    
    @FXML
    private TextField txtfldValorPagamento;
    
    ObservableList<String> metodos_pagamento = FXCollections.observableArrayList("Espécie", "PIX", "Débito", "Crédito");
    
    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
        atualizaLabels();
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
        cmbboxMetodoPagamento.getSelectionModel().select(0);
    }    
    
    private void atualizaLabels() {
        lblNumeroParcela.setText(Integer.toString(this.parcela.getNumero_parcela()));
        lblValorParcela.setText("R$ " + this.parcela.getValor_parcela().toString() + "0");
        txtfldValorPagamento.setText(this.parcela.getValor_parcela().toString());
    }
    
    @FXML
    public void quitarParcela(ActionEvent event) throws IOException {
        System.out.println("QUITAR");
        
        //Criar um pagamento para a parcela e atribuir no banco de dads essa atualização (merge), ver como está a lógica de PAGO e A PAGAR na tela anterior Mudar Status de Pendente para Pago
        //Criar um item_caixa que irá entrar na soma atual do caixa já aberto, vai ter que fazer lógica de abrir um novo caixa por aqui também...
        //
    }
    
    @FXML
    public void limparCampos(ActionEvent event) throws IOException {
        System.out.println("LIMPAR CAMPOS");
        txtfldValorPagamento.setText(this.parcela.getValor_parcela().toString());
        cmbboxMetodoPagamento.getSelectionModel().select(0);
    }
    public interface ItemCallback {
        void onItemUpdated();
    }

    public void setItemCallBack(ItemCallback itemCallBack) {
        this.itemCallBack = itemCallBack;
    }
    
    
}
