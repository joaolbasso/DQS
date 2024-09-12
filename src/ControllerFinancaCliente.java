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

        id_parcela.setCellValueFactory(new PropertyValueFactory<>("id_parcela"));
        data_vencimento.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
        valor_parcela.setCellValueFactory(new PropertyValueFactory<>("valor_parcela"));

        condicao.setCellValueFactory(new PropertyValueFactory<>("condicao"));
        
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
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        preencherDadosCliente();
        carregarParcelasDoCliente();
    }

    private void preencherDadosCliente() {
        // Definindo os dados do cliente nos labels
        nome_cliente.setText("Cliente: " + cliente.getNome_cliente());
        cpf_cliente.setText("CPF: " + cliente.getCpf());
        telefone_cliente.setText("Telefone: " + cliente.getTelefone());
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
