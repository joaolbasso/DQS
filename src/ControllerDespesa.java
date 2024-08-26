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
    public void voltar(ActionEvent event) throws IOException {
        // Substitua "NomeDaView" pelo nome da view que você deseja carregar
        String nomeDaView = "MenuPrincipal.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
        Parent view = loader.load();

        Scene cena = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cena);
        window.show();
    }

    @FXML
    private TableView<Despesa> tbvwDespesas;

    public void atualizarListaDespesas() {
        DespesaDAO despesaDAO = new DespesaDAO();
        ObservableList<Despesa> despesas = FXCollections.observableArrayList(despesaDAO.todasAsDespesas());
        tbvwDespesas.setItems(despesas);
    }

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
    private Button btnRegistrarDespesa;

    @FXML
    public void entrarRegistrarDespesa(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RegistrarDespesa.fxml"));
        Parent registrarDespesaView = loader.load();

        ControllerRegistrarDespesa controllerRegistrarDespesa = loader.getController();
        controllerRegistrarDespesa.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene registrarDespesaScene = new Scene(registrarDespesaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registrarDespesaScene);
        window.show();
    }

    @FXML
    public void editarDespesa(ActionEvent event) throws IOException {
        Despesa despesaSelecionada = tbvwDespesas.getSelectionModel().getSelectedItem();
        if (despesaSelecionada != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RegistrarDespesa.fxml"));
            Parent registrarDespesaView = loader.load();

            ControllerRegistrarDespesa controllerRegistrarDespesa = loader.getController();
            controllerRegistrarDespesa.setDespesa(despesaSelecionada);

            Scene registrarDespesaScene = new Scene(registrarDespesaView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(registrarDespesaScene);
            window.show();
        }
    }

    @FXML
    public void deletarDespesa(ActionEvent event) throws IOException {
        Despesa despesaSelecionada = tbvwDespesas.getSelectionModel().getSelectedItem();
        if (despesaSelecionada != null) {
            DespesaDAO despesaDAO = new DespesaDAO();
            despesaDAO.delete(despesaSelecionada);
            atualizarListaDespesas();  // Atualiza a lista de despesas após a exclusão
        }
    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nome_despesa.setCellValueFactory(new PropertyValueFactory<>("nome_despesa"));
        valor_despesa.setCellValueFactory(new PropertyValueFactory<>("valor_despesa"));
        data_pagamento_despesa.setCellValueFactory(new PropertyValueFactory<>("data_pagamento_despesa"));
        data_vencimento_despesa.setCellValueFactory(new PropertyValueFactory<>("data_vencimento_despesa"));

        beneficiario.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getBeneficiario().getNome_beneficiario());
        });

        centralizarTextoNaColuna(nome_despesa);
        centralizarTextoNaColuna(valor_despesa);
        centralizarTextoNaColuna(data_pagamento_despesa);
        centralizarTextoNaColuna(data_vencimento_despesa);
        centralizarTextoNaColuna(beneficiario);

        atualizarListaDespesas();
    }
}
