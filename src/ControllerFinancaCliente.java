import DAO.ClienteDAO;
import DAO.VendaDAO;
import DAO.ParcelaDAO;
import DAO.PagamentoDAO;
import Model.Cliente;
import Model.Parcela;
import Model.Pagamento;
import Model.Venda;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerFinancaCliente implements Initializable {

    private Cliente cliente;

    @FXML
    private Label nome_cliente;

    @FXML
    private Label cpf_cliente;

    @FXML
    private Label telefone_cliente;

    @FXML
    private TableView<Parcela> tbvwParcelas;

    @FXML
    private TableColumn<Parcela, Integer> id_venda;

    @FXML
    private TableColumn<Parcela, Integer> id_parcela;

    @FXML
    private TableColumn<Parcela, LocalDate> data_vencimento;

    @FXML
    private TableColumn<Parcela, Double> valor_parcela;

    @FXML
    private TableColumn<Parcela, String> condicao;
    
    @FXML
    private TableColumn<Parcela, Void> pagarColuna = new TableColumn<>("Receber Parcela");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializando as colunas da tabela
        id_venda.setCellValueFactory(cellData -> {
            Parcela parcela = cellData.getValue();
            if (parcela != null && parcela.getVenda() != null) {
                return new javafx.beans.property.SimpleIntegerProperty(parcela.getVenda().getId_venda()).asObject();
            } else {
                return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
            }
        });

        id_parcela.setCellValueFactory(new PropertyValueFactory<>("numero_parcela"));
        data_vencimento.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
        valor_parcela.setCellValueFactory(new PropertyValueFactory<>("valor_parcela"));

        condicao.setCellValueFactory(new PropertyValueFactory<>("condicao"));
        
        centralizarTextoNaColuna(condicao);
        centralizarTextoNaColuna(id_parcela);
        centralizarTextoNaColuna(data_vencimento);
        centralizarTextoNaColuna(valor_parcela);
        centralizarTextoNaColuna(id_venda);
        
        
        condicao.setCellFactory(column -> new TableCell<Parcela, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
        adicionarBotaoTabela();
        
        tbvwParcelas.getColumns().addAll(pagarColuna);
    }
    
    private void adicionarBotaoTabela() {
        
            pagarColuna.setCellFactory(coluna -> {
            return new TableCell<Parcela, Void>() {
                private final Button btnPagar = new Button();
                private final ImageView ivMoeda = new ImageView(new Image(getClass().getResourceAsStream("/View/Imagens/Icons/moeda.png")));
                {
                    ivMoeda.setFitHeight(16);
                    ivMoeda.setFitWidth(16);
                    btnPagar.setGraphic(ivMoeda);
                    btnPagar.setStyle("-fx-background-color: transparent;");

                    btnPagar.setOnAction(event -> {
                        try {
                            Parcela parcela = (Parcela) getTableRow().getItem();
                            pagamentoParcela(event, parcela);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
            } else {
                Parcela parcela = (Parcela) getTableRow().getItem();
                if ("A Pagar".equals(parcela.getCondicao())) {
                    setGraphic(btnPagar);
                    setStyle("-fx-alignment: CENTER;");
                } else {
                    setGraphic(null);
                }
            }
        }

            };
        });
}
    
        private void pagamentoParcela(ActionEvent event, Parcela parcela) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PagamentoParcelado.fxml"));
            Parent pagamentoParcelaView = loader.load();

            ControllerPagamentoParcelado controllerPagamentoParcelado = loader.getController();
            controllerPagamentoParcelado.setCenaAnterior(((Node) event.getSource()).getScene());
            controllerPagamentoParcelado.setParcela(parcela);
            
            controllerPagamentoParcelado.setItemCallBack(() -> {
            // Atualizar a ComboBox
            carregarParcelasDoCliente();
            });
       
            Scene cadastrarClienteScene = new Scene(pagamentoParcelaView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(cadastrarClienteScene);
            window.show();
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        preencherDadosCliente();
        carregarParcelasDoCliente();
    }

    private void preencherDadosCliente() {
        // Definindo os dados do cliente nos labels
        nome_cliente.setText(cliente.getNome_cliente());
        cpf_cliente.setText(cliente.getCpf());
        telefone_cliente.setText(cliente.getTelefone());
    }

    private void carregarParcelasDoCliente() {
        VendaDAO vendaDAO = new VendaDAO();
        ParcelaDAO parcelaDAO = new ParcelaDAO();
        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        // Buscar todas as vendas do cliente
        List<Venda> vendas = vendaDAO.listaVendasPorCliente(cliente.getId_cliente());

        // Lista para armazenar as parcelas
        List<Parcela> parcelasList = new ArrayList<>();

        // Buscar as parcelas para cada venda
        for (Venda venda : vendas) {
            List<Parcela> parcelas = parcelaDAO.listarParcelasPorVenda(venda.getId_venda());
            for (Parcela parcela : parcelas) {
                // Verificar se a parcela já foi paga
                List<Pagamento> pagamentos = pagamentoDAO.listarPagamentosPorParcela(parcela.getId_parcela());
                String condicao = pagamentos.isEmpty() ? "A Pagar" : "Pago";
                parcela.setCondicao(condicao); // Atualiza a condição
                parcelasList.add(parcela);
            }
        }

        // Converter para ObservableList e definir na TableView
        ObservableList<Parcela> observableParcelas = FXCollections.observableArrayList(parcelasList);
        tbvwParcelas.setItems(observableParcelas);
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        String nomeDaView = "Cliente.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
        Parent view = loader.load();

        Scene cena = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cena);
        window.show();
    }

}
