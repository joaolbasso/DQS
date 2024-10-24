import DAO.ClienteDAO;
import Model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerCliente implements Initializable {

    @FXML
    private TableView<Cliente> tbvwClientes;

    @FXML
    private TableColumn<Cliente, String> nome_cliente = new TableColumn<>("Nome");

    @FXML
    private TableColumn<Cliente, String> cpf = new TableColumn<>("CPF");

    @FXML
    private TableColumn<Cliente, String> telefone = new TableColumn<>("Telefone");

    @FXML
    private TableColumn<Cliente, Void> editarColuna = new TableColumn<>("");
    
    @FXML
    private TableColumn<Cliente, Void> financaColuna = new TableColumn<>("");
    
    @FXML
    private TableColumn<Cliente,Boolean> situacaoColuna = new TableColumn<>("");

    @FXML
    private SplitMenuButton spmbFiltro;

    @FXML
    private TextField txtFiltro;

    @FXML
    private Button btnConsultar;
    
    @FXML
    private Button btnLimparFiltro;

    private ClienteDAO clienteDAO = new ClienteDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarOpcoesFiltro();
        spmbFiltro.setText("Nome");
        btnLimparFiltro.setOnAction(event -> limparFiltro());
        btnConsultar.setOnAction(event -> aplicarFiltro());

        nome_cliente.setCellValueFactory(new PropertyValueFactory<>("nome_cliente"));
        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        alinharTextoNaColuna(nome_cliente, "CENTER-LEFT");
        alinharTextoNaColuna(cpf, "CENTER-LEFT");
        alinharTextoNaColuna(telefone, "CENTER-LEFT");

        adicionarBotoesTabela();
        atualizarListaClientes();
    }

    private void configurarOpcoesFiltro() {
        MenuItem filtrarPorNome = new MenuItem("Nome");
        spmbFiltro.getItems().addAll(filtrarPorNome);

        for (MenuItem item : spmbFiltro.getItems()) {
            item.setOnAction(event -> spmbFiltro.setText(item.getText()));
        }
    }

    private void aplicarFiltro() {
        String filtro = txtFiltro.getText().toLowerCase();
        String criterio = spmbFiltro.getText();
        ClienteDAO clienteDAO = new ClienteDAO();

        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());

        ObservableList<Cliente> clientesFiltrados = clientes.filtered(cliente -> {
            switch (criterio) {
                case "Nome":
                    return cliente.getNome_cliente().toLowerCase().contains(filtro);
                default:
                    return true;
            }
        });

        tbvwClientes.setItems(clientesFiltrados);
    }

    @FXML
    private void limparFiltro() {
        txtFiltro.clear();
        spmbFiltro.setText("Nome");
        atualizarListaClientes();
    }

    private void adicionarBotoesTabela() {
        
        // Botão Editar
        editarColuna.setCellFactory(coluna -> {
            return new TableCell<Cliente, Void>() {
                private final Button btnEditar = new Button();
                private final ImageView ivEditar = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/editar.png")));
                {
                    ivEditar.setFitHeight(16);
                    ivEditar.setFitWidth(16);
                    btnEditar.setGraphic(ivEditar);
                    btnEditar.setStyle("-fx-background-color: transparent;");

                    btnEditar.setOnAction(event -> {
                        Cliente clienteSelecionada = getTableView().getItems().get(getIndex());
                        try {
                            editarCliente(event, clienteSelecionada);
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
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
        });

        // Botão Finanças
        financaColuna.setCellFactory(coluna -> {
            return new TableCell<Cliente, Void>() {
                private final Button btnFinanceiro = new Button();
                private final ImageView ivFinanca = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/financaCliente.png")));

                {
                    ivFinanca.setFitHeight(16);
                    ivFinanca.setFitWidth(16);
                    btnFinanceiro.setGraphic(ivFinanca);
                    btnFinanceiro.setStyle("-fx-background-color: transparent;");

                    btnFinanceiro.setOnAction(event -> {
                        Cliente clienteSelecionada = getTableView().getItems().get(getIndex());
                        try {
                            financaCliente(event, clienteSelecionada);
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
                        setGraphic(btnFinanceiro);
                        setStyle("-fx-alignment: CENTER;");
                    }
                }
            };
        });
        
        situacaoColuna.setCellValueFactory(param -> {
        Cliente cliente = param.getValue();
        // Verifica se o cliente tem parcelas em aberto
        boolean hasParcelasEmAberto = clienteDAO.clientePorNomeTemParcelasEmAberto(cliente.getNome_cliente());
        return new SimpleBooleanProperty(hasParcelasEmAberto);
        });
        
        situacaoColuna.setCellFactory(col -> new TableCell<Cliente, Boolean>() {
            private final ImageView ivOK = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/ok.png")));
            private final ImageView ivNOTOK = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/notok.png")));

        @Override
        protected void updateItem(Boolean hasParcelasEmAberto, boolean empty) {
            super.updateItem(hasParcelasEmAberto, empty);
            if (empty || hasParcelasEmAberto == null) {
                setGraphic(null);
            } else {
                if (hasParcelasEmAberto) {
                    ivNOTOK.setFitHeight(16);
                    ivNOTOK.setFitWidth(16);
                    
                    setGraphic(ivNOTOK);
                } else {
                    ivOK.setFitHeight(16);
                    ivOK.setFitWidth(16);
                    setGraphic(ivOK);
                }
                setStyle("-fx-alignment: CENTER;");
            }
        }
        });

        situacaoColuna.setComparator(Boolean::compareTo);
        tbvwClientes.getColumns().addAll(editarColuna, financaColuna, situacaoColuna);
    }

    public void editarCliente(ActionEvent event, Cliente clienteSelecionada) throws IOException {
        if (clienteSelecionada != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarCliente.fxml"));
            Parent cadastrarClienteView = loader.load();

            ControllerCadastrarCliente controllerCadastrarCliente = loader.getController();
            controllerCadastrarCliente.setCliente(clienteSelecionada);
            controllerCadastrarCliente.setCenaAnterior(((Node) event.getSource()).getScene());
            
            controllerCadastrarCliente.setItemCallBack(() -> {
            // Atualizar a ComboBox
            atualizarListaClientes();
            });

            
            Scene cadastrarClienteScene = new Scene(cadastrarClienteView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(cadastrarClienteScene);
            window.show();
        }
    }

    public void financaCliente(ActionEvent event, Cliente clienteSelecionada) throws IOException {
        if (clienteSelecionada != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FinancaCliente.fxml"));
            Parent financaClienteView = loader.load();

            ControllerFinancaCliente controllerFinancaCliente = loader.getController();
            controllerFinancaCliente.setCliente(clienteSelecionada);
            //controllerFinancaCliente.setCenaAnterior(((Node) event.getSource()).getScene());

            Scene financaClienteScene = new Scene(financaClienteView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(financaClienteScene);
            window.show();
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

    public void atualizarListaClientes() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());
        tbvwClientes.setItems(clientes);
    }

    @FXML
    public void entrarCadastrarCliente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarCliente.fxml"));
        Parent cadastrarClienteView = loader.load();

        ControllerCadastrarCliente controllerCadastrarCliente = loader.getController();
        controllerCadastrarCliente.setCenaAnterior(((Node) event.getSource()).getScene());
        
        controllerCadastrarCliente.setItemCallBack(() -> {
        // Atualizar a ComboBox
        atualizarListaClientes();
        });
       
        Scene cadastrarClienteScene = new Scene(cadastrarClienteView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cadastrarClienteScene);
        window.show();
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
}

