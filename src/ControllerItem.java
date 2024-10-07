import DAO.ItemDAO;
import Model.Item;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class ControllerItem implements Initializable {

    @FXML
    private TableView<Item> tbvwItens;

    @FXML
    private TableColumn<Item, String> nome_item = new TableColumn<>("Nome");

    @FXML
    private TableColumn<Item, Double> valor_item = new TableColumn<>("Valor");

    //@FXML
    //private TableColumn<Item, Integer> quantidade = new TableColumn<>("Quantidade");

    @FXML
    private TableColumn<Item, Character> tipo_item = new TableColumn<>("Tipo");

    @FXML
    private TableColumn<Item, Void> editarColuna = new TableColumn<>("");

    @FXML
    private TableColumn<Item, Void> deletarColuna = new TableColumn<>("");

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

        // Configura a ação do botão Limpar Filtro
        btnLimparFiltro.setOnAction(event -> limparFiltro());

        // Ação do botão Consultar
        btnConsultar.setOnAction(event -> aplicarFiltro());

        nome_item.setCellValueFactory(new PropertyValueFactory<>("nome_item"));
        valor_item.setCellValueFactory(new PropertyValueFactory<>("valor_item"));
        //quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tipo_item.setCellValueFactory(new PropertyValueFactory<>("tipo_item"));

        // Define o tamanho das colunas
        nome_item.setPrefWidth(325);
        valor_item.setPrefWidth(200);
        //quantidade.setPrefWidth(100);
        tipo_item.setPrefWidth(100);

        alinharTextoNaColuna(nome_item, "CENTER-LEFT");
        alinharTextoNaColuna(valor_item, "CENTER-RIGHT");
        //alinharTextoNaColuna(quantidade);
        alinharTextoNaColuna(tipo_item, "CENTER");
        
        formatarMoedaNaColuna(valor_item);

        adicionarBotoesTabela();
        ajustarLarguraTabela();
        atualizarListaItens();
    }

    private void configurarOpcoesFiltro() {
        MenuItem filtrarPorNome = new MenuItem("Nome");
        MenuItem filtrarPorTipo = new MenuItem("Tipo");

        // Adiciona as opções ao SplitMenuButton
        spmbFiltro.getItems().addAll(filtrarPorNome, filtrarPorTipo);

        // Define a opção "Nome" como padrão
        spmbFiltro.setText("Nome");

        // Define a ação para as opções do SplitMenuButton
        for (MenuItem item : spmbFiltro.getItems()) {
            item.setOnAction(event -> spmbFiltro.setText(item.getText()));
        }
    }

    private void aplicarFiltro() {
        String filtro = txtFiltro.getText().toUpperCase(); // Converter para maiúsculo para consistência
        String criterio = spmbFiltro.getText();
        ItemDAO itemDAO = new ItemDAO();

        // Recupera todas as items
        ObservableList<Item> itens = FXCollections.observableArrayList(itemDAO.todosOsItens());

        // Filtra as items com base no critério e no valor fornecido
        ObservableList<Item> itensFiltrados = itens.filtered(item -> {
            switch (criterio) {
                case "Nome":
                    return item.getNome_item().toLowerCase().contains(filtro.toLowerCase());
                case "Tipo":
                    if (filtro.isEmpty()) {
                        return true; // Mostrar todos se não houver filtro para o tipo
                    }
                    // Verifica se o filtro contém exatamente um caractere
                    if (filtro.length() != 1) {
                        return false;
                    }
                    char tipoFiltro = filtro.charAt(0); // Espera-se que o filtro contenha apenas um caractere para o tipo
                    return item.getTipo_item() == tipoFiltro;
                default:
                    return true;
            }
        });

        // Atualiza a TableView com as items filtradas
        tbvwItens.setItems(itensFiltrados);
    }

    @FXML
    private void limparFiltro() {
        txtFiltro.clear();
        spmbFiltro.setText("Nome"); // Retorna para "Nome" como padrão
        atualizarListaItens();
    }

    private void adicionarBotoesTabela() {
        // Coluna Editar
        editarColuna.setCellFactory(coluna -> {
            return new TableCell<Item, Void>() {
                private final Button btnEditar = new Button();
                private final ImageView ivEditar = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/editar.png")));

                {
                    ivEditar.setFitHeight(15);
                    ivEditar.setFitWidth(15);
                    btnEditar.setGraphic(ivEditar);
                    btnEditar.setStyle("-fx-background-color: transparent;"); // Remove fundo do botão

                    btnEditar.setOnAction(event -> {
                        Item itemSelecionado = getTableView().getItems().get(getIndex());
                        try {
                            editarItem(event, itemSelecionado);
                        } catch (IOException ex) {
                            Logger.getLogger(ControllerItem.class.getName()).log(Level.SEVERE, null, ex);
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
            return new TableCell<Item, Void>() {
                private final Button btnDeletar = new Button();
                private final ImageView ivDeletar = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/deletar.png")));

                {
                    ivDeletar.setFitHeight(16);
                    ivDeletar.setFitWidth(16);
                    btnDeletar.setGraphic(ivDeletar);
                    btnDeletar.setStyle("-fx-background-color: transparent;"); // Remove fundo do botão

                    btnDeletar.setOnAction(event -> {
                        Item itemSelecionado = getTableView().getItems().get(getIndex());

                        // Confirmação para exclusão
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmação de Exclusão");
                        alert.setHeaderText("Tem certeza que deseja deletar este item?");
                        alert.setContentText(itemSelecionado.getNome_item());

                        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                            deletarItem(itemSelecionado);
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

        tbvwItens.getColumns().addAll(editarColuna, deletarColuna);
    }

    public void editarItem(ActionEvent event, Item itemSelecionado) throws IOException {
        if (itemSelecionado != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarItem.fxml"));
            Parent cadastrarItemView = loader.load();

            ControllerCadastrarItem controllerCadastrarItem = loader.getController();
            controllerCadastrarItem.setItem(itemSelecionado);
            controllerCadastrarItem.setCenaAnterior(((Node) event.getSource()).getScene());
            
            controllerCadastrarItem.setItemCallBack(() -> {
                atualizarListaItens();
            });

            Scene cadastrarItemScene = new Scene(cadastrarItemView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(cadastrarItemScene);
            window.show();
        }
    }

    public void deletarItem(Item itemSelecionado) {
        if (itemSelecionado != null) {
            ItemDAO itemDAO = new ItemDAO();
            itemDAO.delete(itemSelecionado);
            atualizarListaItens();  // Atualiza a lista de items após a exclusão
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
                        setStyle("-fx-alignment: CENTER-RIGHT;"); // Alinha o texto à direita
                    }
                }
            };
        }
    });
    }

    private void atualizarListaItens() {
        ItemDAO itemDAO = new ItemDAO();
        ObservableList<Item> itens = FXCollections.observableArrayList(itemDAO.todosOsItens());
        tbvwItens.setItems(itens);
    }

    private void ajustarLarguraTabela() {
        tbvwItens.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
    public void entrarCadastrarItem(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarItem.fxml"));
        Parent cadastrarItemView = loader.load();      

        ControllerCadastrarItem controllerCadastrarItem = loader.getController();
        controllerCadastrarItem.setCenaAnterior(((Node) event.getSource()).getScene());
        
        controllerCadastrarItem.setItemCallBack(() -> {
        // Atualizar a ComboBox
        atualizarListaItens();
        });
        
        Scene cadastrarItemScene = new Scene(cadastrarItemView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cadastrarItemScene);
        window.show();
    }

}
