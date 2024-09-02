import DAO.ClienteDAO;
import Model.Cliente;
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

public class ControllerCliente implements Initializable {

    @FXML
    private TableView<Cliente> tbvwClientes;

    @FXML
    private TableColumn<Cliente, Integer> id_cliente = new TableColumn<>("Id");

    @FXML
    private TableColumn<Cliente, String> nome_cliente = new TableColumn<>("Nome");

    @FXML
    private TableColumn<Cliente, String> cpf = new TableColumn<>("CPF");

    @FXML
    private TableColumn<Cliente, String> telefone = new TableColumn<>("Telefone");

    @FXML
    private TableColumn<Cliente, Void> editarColuna = new TableColumn<>("");

    @FXML
    private TableColumn<Cliente, Void> deletarColuna = new TableColumn<>("");

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

        id_cliente.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));
        nome_cliente.setCellValueFactory(new PropertyValueFactory<>("nome_cliente"));
        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        centralizarTextoNaColuna(id_cliente);
        centralizarTextoNaColuna(nome_cliente);
        centralizarTextoNaColuna(cpf);
        centralizarTextoNaColuna(telefone);
        

        adicionarBotoesTabela();
        atualizarListaClientes();
    }
   
    // Método para configurar as opções do SplitMenuButton
    private void configurarOpcoesFiltro() {
        MenuItem filtrarPorNome = new MenuItem("Nome");

        // Adiciona as opções ao SplitMenuButton
        spmbFiltro.getItems().addAll(filtrarPorNome);

        // Define a opção selecionada no SplitMenuButton
        for (MenuItem item : spmbFiltro.getItems()) {
            item.setOnAction(event -> spmbFiltro.setText(item.getText()));
        }
    }

    // Método para aplicar o filtro na TableView
    private void aplicarFiltro() {
        String filtro = txtFiltro.getText().toLowerCase();
        String criterio = spmbFiltro.getText();
        ClienteDAO clienteDAO = new ClienteDAO();

        // Recupera todas as clientes
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());

        // Filtra as clientes com base no critério e no valor fornecido
        ObservableList<Cliente> clientesFiltrados = clientes.filtered(cliente -> {
            switch (criterio) {
                case "Nome":
                    return cliente.getNome_cliente().toLowerCase().contains(filtro);
                default:
                    return true;
            }
        });

        // Atualiza a TableView com as clientes filtradas
        tbvwClientes.setItems(clientesFiltrados);
    }

    @FXML
    private void limparFiltro() {
        txtFiltro.clear(); 
        spmbFiltro.setText(""); 

        atualizarListaClientes();
    }
    
private void adicionarBotoesTabela() {
        // Coluna Editar
        editarColuna.setCellFactory(coluna -> {
            return new TableCell<Cliente, Void>() {
                private final Button btnEditar = new Button();
                private final ImageView ivEditar = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/editar.png")));

                {
                    ivEditar.setFitHeight(16);
                    ivEditar.setFitWidth(16);
                    btnEditar.setGraphic(ivEditar);
                    btnEditar.setStyle("-fx-background-color: transparent;"); // Remove fundo do botão

                    btnEditar.setOnAction(event -> {
                        Cliente clienteSelecionada = getTableView().getItems().get(getIndex());

                        // Confirmação para edição
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmação de Edição");
                        alert.setHeaderText("Tem certeza que deseja editar esta cliente?");
                        alert.setContentText(clienteSelecionada.getNome_cliente());

                        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                            try {
                                editarCliente(event, clienteSelecionada);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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
            return new TableCell<Cliente, Void>() {
                private final Button btnDeletar = new Button();
                private final ImageView ivDeletar = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/deletar.png")));

                {
                    ivDeletar.setFitHeight(16);
                    ivDeletar.setFitWidth(16);
                    btnDeletar.setGraphic(ivDeletar);
                    btnDeletar.setStyle("-fx-background-color: transparent;"); // Remove fundo do botão

                    btnDeletar.setOnAction(event -> {
                        Cliente clienteSelecionada = getTableView().getItems().get(getIndex());

                        // Confirmação para exclusão
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmação de Exclusão");
                        alert.setHeaderText("Tem certeza que deseja deletar esta cliente?");
                        alert.setContentText(clienteSelecionada.getNome_cliente());

                        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                            deletarCliente(clienteSelecionada);
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

        tbvwClientes.getColumns().addAll(editarColuna, deletarColuna);
    }
    
    public void editarCliente(ActionEvent event, Cliente clienteSelecionada) throws IOException {
        if (clienteSelecionada != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarCliente.fxml"));
            Parent cadastrarClienteView = loader.load();

            ControllerCadastrarCliente controllerCadastrarCliente = loader.getController();
            controllerCadastrarCliente.setCliente(clienteSelecionada);

            Scene cadastrarClienteScene = new Scene(cadastrarClienteView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(cadastrarClienteScene);
            window.show();
        }
    }

    public void deletarCliente(Cliente clienteSelecionada) {
        if (clienteSelecionada != null) {
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.delete(clienteSelecionada);
            atualizarListaClientes();  // Atualiza a lista de clientes após a exclusão
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

    public void atualizarListaClientes() {
        ClienteDAO clienteDAO = new ClienteDAO();
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());
        tbvwClientes.setItems(clientes);
    }

    @FXML
    public void entrarCadastrarCliente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarCliente.fxml"));
        Parent cadastrarClienteView = loader.load();      

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