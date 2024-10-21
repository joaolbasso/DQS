import DAO.DespesaDAO;
import Model.Despesa;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerDespesa implements Initializable {

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
    private TableColumn<Despesa, Void> editarColuna = new TableColumn<>("");

    @FXML
    private TableColumn<Despesa, Void> deletarColuna = new TableColumn<>("");
    
    @FXML
    private SplitMenuButton spmbFiltro;

    @FXML
    private TextField txtFiltro;

    @FXML
    private Button btnConsultar;
    
    @FXML
    private Button btnLimparFiltro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configura as opções do SplitMenuButton
        configurarOpcoesFiltro();

        // Define "Nome" como filtro padrão
        spmbFiltro.setText("Nome");

        // Configura a ação do botão Limpar Filtro
        btnLimparFiltro.setOnAction(event -> limparFiltro());

        // Ação do botão Consultar
        btnConsultar.setOnAction(event -> aplicarFiltro());

        nome_despesa.setCellValueFactory(new PropertyValueFactory<>("nome_despesa"));
        valor_despesa.setCellValueFactory(new PropertyValueFactory<>("valor_despesa"));
        beneficiario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBeneficiario().getNome_beneficiario()));

        // Configurar colunas de data
        data_pagamento_despesa.setCellValueFactory(new PropertyValueFactory<>("data_pagamento_despesa"));
        data_vencimento_despesa.setCellValueFactory(new PropertyValueFactory<>("data_vencimento_despesa"));

        alinharTextoNaColuna(nome_despesa, "CENTER-LEFT");
        alinharTextoNaColuna(valor_despesa, "CENTER-RIGHT");
        
        alinharTextoNaColuna(beneficiario, "CENTER-LEFT");
        
        formatarMoedaNaColuna(valor_despesa);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatarDataNaColuna(data_pagamento_despesa, formatter);
        formatarDataNaColuna(data_vencimento_despesa, formatter);
        adicionarBotoesTabela();
        atualizarListaDespesas();
    }

    // Método para configurar as opções do SplitMenuButton
    private void configurarOpcoesFiltro() {
        MenuItem filtrarPorNome = new MenuItem("Nome");
        MenuItem filtrarPorDataPagamento = new MenuItem("Data de Pagamento");
        MenuItem filtrarPorDataVencimento = new MenuItem("Data de Vencimento");
        MenuItem filtrarPorBeneficiario = new MenuItem("Beneficiário");

        // Adiciona as opções ao SplitMenuButton
        spmbFiltro.getItems().addAll(filtrarPorNome, filtrarPorDataPagamento, filtrarPorDataVencimento, filtrarPorBeneficiario);

        // Define a opção selecionada no SplitMenuButton
        for (MenuItem item : spmbFiltro.getItems()) {
            item.setOnAction(event -> spmbFiltro.setText(item.getText()));
        }
    }

    // Método para aplicar o filtro na TableView
    private void aplicarFiltro() {
        String filtro = txtFiltro.getText().toLowerCase();
        String criterio = spmbFiltro.getText();
        DespesaDAO despesaDAO = new DespesaDAO();

        // Recupera todas as despesas
        ObservableList<Despesa> despesas = FXCollections.observableArrayList(despesaDAO.todasAsDespesas());

        // Configura o formato de data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");

        // Filtra as despesas com base no critério e no valor fornecido
        ObservableList<Despesa> despesasFiltradas = despesas.filtered(despesa -> {
            switch (criterio) {
                case "Nome":
                    return despesa.getNome_despesa().toLowerCase().contains(filtro);
                case "Data de Pagamento":
                    return despesa.getData_pagamento_despesa() != null &&
                           despesa.getData_pagamento_despesa().format(formatter).contains(filtro);
                case "Data de Vencimento":
                    return despesa.getData_vencimento_despesa() != null &&
                           despesa.getData_vencimento_despesa().format(formatter).contains(filtro);
                case "Beneficiário":
                    return despesa.getBeneficiario().getNome_beneficiario().toLowerCase().contains(filtro);
                default:
                    return true;
            }
        });

        // Atualiza a TableView com as despesas filtradas
        tbvwDespesas.setItems(despesasFiltradas);
    }
    
    @FXML
    private void limparFiltro() {
        txtFiltro.clear(); 
        spmbFiltro.setText("Nome"); 

        atualizarListaDespesas();
    }
    
    // Método atualizado para adicionar botões à tabela
    private void adicionarBotoesTabela() {
        // Coluna Editar
        editarColuna.setCellFactory(coluna -> {
            return new TableCell<Despesa, Void>() {
                private final Button btnEditar = new Button();
                private final ImageView ivEditar = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/editar.png")));

                {
                    ivEditar.setFitHeight(16);
                    ivEditar.setFitWidth(16);
                    btnEditar.setGraphic(ivEditar);
                    btnEditar.setStyle("-fx-background-color: transparent;"); // Remove fundo do botão

                    btnEditar.setOnAction(event -> {
                        Despesa despesaSelecionada = getTableView().getItems().get(getIndex());
                            try {
                                editarDespesa(event, despesaSelecionada);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnEditar);
                        setStyle("-fx-alignment: CENTER;"); // Centraliza o ícone
                    }
                }
            };
        });

        // Coluna Deletar
        deletarColuna.setCellFactory(coluna -> {
            return new TableCell<Despesa, Void>() {
                private final Button btnDeletar = new Button();
                private final ImageView ivDeletar = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/deletar.png")));

                {
                    ivDeletar.setFitHeight(16);
                    ivDeletar.setFitWidth(16);
                    btnDeletar.setGraphic(ivDeletar);
                    btnDeletar.setStyle("-fx-background-color: transparent;"); // Remove fundo do botão

                    btnDeletar.setOnAction(event -> {
                        Despesa despesaSelecionada = getTableView().getItems().get(getIndex());

                        // Confirmação para exclusão
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmação de Exclusão");
                        alert.setHeaderText("Tem certeza que deseja deletar esta despesa?");
                        alert.setContentText(despesaSelecionada.getNome_despesa());

                        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                            deletarDespesa(despesaSelecionada);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btnDeletar);
                        setStyle("-fx-alignment: CENTER;"); // Centraliza o ícone
                    }
                }
            };
        });

        tbvwDespesas.getColumns().addAll(editarColuna, deletarColuna);
    }

    public void editarDespesa(ActionEvent event, Despesa despesaSelecionada) throws IOException {
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
        else{
            System.out.println("Passou");
        }
    }

    public void deletarDespesa(Despesa despesaSelecionada) {
        if (despesaSelecionada != null) {
            DespesaDAO despesaDAO = new DespesaDAO();
            despesaDAO.delete(despesaSelecionada);
            atualizarListaDespesas();  // Atualiza a lista de despesas após a exclusão
        }
    }

    private <T, U> void alinharTextoNaColuna(TableColumn<T, U> coluna, String posicao) {
        coluna.setCellFactory(column -> new TableCell<T, U>() {
            @Override
            protected void updateItem(U item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment:" +  posicao + ";"); // Centraliza o texto
                }
            }
        });
    }
    
    private <T> void formatarMoedaNaColuna(TableColumn<T, Double> coluna) {
    coluna.setCellFactory(new Callback<TableColumn<T, Double>, TableCell<T, Double>>() {
        @Override
        public TableCell<T, Double> call(TableColumn<T, Double> param) {
            return new TableCell<T, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                        setText(currencyFormat.format(item));
                        setStyle("-fx-alignment: CENTER-RIGHT;");
                    }
                }
            };
        }
    });
}
    
    private <T, U> void formatarDataNaColuna(TableColumn<T, U> coluna, DateTimeFormatter formatter) {
    coluna.setCellFactory(new Callback<TableColumn<T, U>, TableCell<T, U>>() {
        @Override
        public TableCell<T, U> call(TableColumn<T, U> param) {
            return new TableCell<T, U>() {
                @Override
                protected void updateItem(U item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        if (item instanceof LocalDateTime || item instanceof LocalDate) {
                            setText(formatter.format((TemporalAccessor) item));
                        } else {
                            setText(item.toString());
                        }
                        setStyle("-fx-alignment: CENTER;"); // Alinha o texto à direita
                    }
                }
            };
        }
    });
}

    public void atualizarListaDespesas() {
        DespesaDAO despesaDAO = new DespesaDAO();
        ObservableList<Despesa> despesas = FXCollections.observableArrayList(despesaDAO.todasAsDespesas());
        tbvwDespesas.setItems(despesas);
    }
    
    @FXML
    public void voltar(ActionEvent event) throws IOException {
        String nomeDaView = "MenuPrincipal.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
        Parent view = loader.load();

        Scene cena = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cena);
        window.show();
    }
    
    @FXML
    public void entrarRegistrarDespesa(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/RegistrarDespesa.fxml"));
        Parent registrarDespesaView = loader.load();      

        Scene registrarDespesaScene = new Scene(registrarDespesaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(registrarDespesaScene);
        window.show();
    }

}
