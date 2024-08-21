import DAO.DespesaDAO;
import Model.Despesa;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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


public class ControllerDespesa implements Initializable {

    @FXML
    private Button btnRegistrarDespesa;
    
    @FXML
    private TableView<Despesa> tbvwDespesas;
    
    @FXML
    private TableColumn<Despesa, String> nome_despesa = new TableColumn<>("Nome");
    
    @FXML
    private TableColumn<Despesa, Double> valor_despesa = new TableColumn<>("Valor");
    
    @FXML
    private TableColumn<Despesa, LocalDate> data_pagamento_despesa = new TableColumn<>("Data de Pagamento");
    
    @FXML
    private TableColumn<Despesa, LocalDate> data_vencimento_despesa = new TableColumn<>("Data de Vencimento");
    
    @FXML
    private TableColumn<Despesa, String> beneficiario = new TableColumn<>("Beneficiario");
    
    
    
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
    
    DespesaDAO despesaDAO = new DespesaDAO();
    ObservableList<Despesa> despesas = FXCollections.observableArrayList(despesaDAO.todasAsDespesas());
    

    private <T, U> void centralizarTextoNaColuna(TableColumn<T, U> coluna) {
    coluna.setCellFactory(column -> new TableCell<T, U>() {
        @Override
        protected void updateItem(U item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                setText(item.toString());
                setStyle("-fx-alignment: CENTER;");
            }
        }
    });
}

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nome_despesa.setCellValueFactory(new PropertyValueFactory<>("nome_despesa"));
        valor_despesa.setCellValueFactory(new PropertyValueFactory<>("valor_despesa"));
        data_pagamento_despesa.setCellValueFactory(new PropertyValueFactory<>("data_pagamento_despesa"));
        data_vencimento_despesa.setCellValueFactory(new PropertyValueFactory<>("data_vencimento_despesa"));
        
        beneficiario.setCellValueFactory(cellData -> 
        {
            return new SimpleStringProperty(cellData.getValue().getBeneficiario().getNome_beneficiario());
        });
        
        centralizarTextoNaColuna(nome_despesa);
        centralizarTextoNaColuna(valor_despesa);
        centralizarTextoNaColuna(data_pagamento_despesa);
        centralizarTextoNaColuna(data_vencimento_despesa);
        centralizarTextoNaColuna(beneficiario);
        
        
        tbvwDespesas.setItems(despesas);
    }    
    
}