import DAO.VendaDAO;
import Model.Cliente;
import Model.Venda;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerFinancaCliente implements Initializable {

    private Cliente cliente;

    @FXML
    private Label txtNome_cliente;

    @FXML
    private Label txtCpf_cliente;

    @FXML
    private Label txtTelefone_cliente;

    @FXML
    private TableView<Venda> tbvwVenda;

    @FXML
    private TableColumn<Venda, Integer> id_venda;

    @FXML
    private TableColumn<Venda, String> data_vencimento;

    @FXML
    private TableColumn<Venda, Double> valor_parcela;

    @FXML
    private TableColumn<Venda, Double> valor_total;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialização da tabela
        id_venda.setCellValueFactory(new PropertyValueFactory<>("id_venda"));
        data_vencimento.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
        valor_parcela.setCellValueFactory(new PropertyValueFactory<>("valor_parcela"));
        valor_total.setCellValueFactory(new PropertyValueFactory<>("valor_total"));

        // Centraliza as colunas (opcional)
        centralizarTextoNaColuna(id_venda);
        centralizarTextoNaColuna(data_vencimento);
        centralizarTextoNaColuna(valor_parcela);
        centralizarTextoNaColuna(valor_total);
    }

    // Preenche os dados do cliente
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        // Define os textos dos Labels para o formato desejado
        txtNome_cliente.setText("Cliente: " + cliente.getNome_cliente());
        txtCpf_cliente.setText("CPF: " + cliente.getCpf());
        txtTelefone_cliente.setText("Telefone: " + cliente.getTelefone());

        // Carrega as vendas do cliente
        //carregarVendasDoCliente();
    }

    // Método para carregar as vendas do cliente na TableView
//    private void carregarVendasDoCliente() {
//        VendaDAO vendaDAO = new VendaDAO();
//        List<Venda> vendas = vendaDAO.obterVendasPorCliente(cliente.getId_cliente()); // Método fictício para buscar as vendas por cliente
//        ObservableList<Venda> vendasList = FXCollections.observableArrayList(vendas);
//        tbvwVenda.setItems(vendasList);
//    }

    // Método opcional para centralizar o texto nas colunas
    private <T, U> void centralizarTextoNaColuna(TableColumn<T, U> coluna) {
        coluna.setCellFactory(column -> new javafx.scene.control.TableCell<T, U>() {
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
}
