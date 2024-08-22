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

    private Scene cenaAnterior;

    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

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
    
    @FXML
    public void voltar(ActionEvent event) throws IOException {
        // Retornar para a cena anterior se existir
        if (cenaAnterior != null) {
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(cenaAnterior);
            window.show();
        }
    }
    
    @FXML
    public void entrarRegistrarDespesa(ActionEvent event) throws IOException {
        // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RegistrarDespesa.fxml"));
        Parent registrarDespesaView = loader.load();

        // Obter o controller da nova tela
        ControllerRegistrarDespesa controllerRegistrarDespesa = loader.getController();

        // Definir a cena atual como a anterior no controller da nova tela
        controllerRegistrarDespesa.setCenaAnterior(((Node) event.getSource()).getScene());

        // Mudar para a nova cena
        Scene registrarDespesaScene = new Scene(registrarDespesaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registrarDespesaScene);
        window.show();
    }

    
    public void editarDespesa(ActionEvent event) throws IOException {
        Parent despesaView = FXMLLoader.load(getClass().getResource("/View/EditarDespesa.fxml"));
        Scene despesaScene = new Scene(despesaView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(despesaScene);
        window.show();
    }
    
    public int getId_Despesa_selecionada() {
        Despesa despesa_seleceionada = tbvwDespesas.getSelectionModel().getSelectedItem();
        int id_despesa_selecionada = despesa_seleceionada.getId_despesa();
        return id_despesa_selecionada;
    }
    
    public void deletarDespesa(ActionEvent event) throws IOException {
        System.out.println("BOTAO Deletar DESPESA CLICK");
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