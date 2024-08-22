/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import DAO.ClienteDAO;
import DAO.ItemDAO;
import Model.Cliente;
import Model.Item;
import Model.Item_venda;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

public class ControllerCaixa implements Initializable {

    private Scene cenaAnterior;

    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }
    
    @FXML
    private DatePicker dtpkrDataVenda;
    @FXML
    private ComboBox<Item> cmbboxItem;
    /*
    @FXML
    private RadioButton rbtnServico;
    @FXML
    private RadioButton rbtnProduto;
    @FXML
    private ToggleGroup tipo_item;
    */
    @FXML
    private Spinner<Integer> spnrQuantidade;
    @FXML
    private ComboBox<Cliente> cmbboxCliente;
    @FXML
    private TextField txtfldValorUnitario;
    @FXML
    private TextField txtfldPreco;
    @FXML
    private Button btnFecharCaixa;
    @FXML
    private Button btnAbrirCaixa;
    @FXML
    private Label lblValorTotal;
    @FXML
    private TableView<Item_venda> tbvwItensVenda;
    @FXML
    private TableColumn<Item_venda, String> tbclnProdutoServico = new TableColumn<>("Produto/Serviço");
    @FXML
    private TableColumn<Item_venda, Integer> tbclnQuantidade = new TableColumn<>("Quantidade");
    @FXML
    private TableColumn<Item_venda, Double> tbclnValor = new TableColumn<>("Valor");
    
    
    private ArrayList<Item_venda> itensDaVenda = new ArrayList<>();
    
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
    public void cadastrarItem(ActionEvent event) throws IOException {
        // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarItem.fxml"));
        Parent caixaView = loader.load();

        // Obter o controller da nova tela
        ControllerCadastrarItem controllerCadastrarItem = loader.getController();

        // Definir a cena atual como a anterior no controller da nova tela
        controllerCadastrarItem.setCenaAnterior(((Node) event.getSource()).getScene());

        // Mudar para a nova cena
        Scene caixaScene = new Scene(caixaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
    }
    
    @FXML
    public void finalizarVenda(ActionEvent event) throws IOException {
        // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Pagamento.fxml"));
        Parent caixaView = loader.load();

        // Obter o controller da nova tela
        ControllerPagamento controllerPagamento = loader.getController();

        // Definir a cena atual como a anterior no controller da nova tela
        controllerPagamento.setCenaAnterior(((Node) event.getSource()).getScene());

        // Mudar para a nova cena
        Scene caixaScene = new Scene(caixaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
    }
    
    @FXML
    public void abrirCaixa(ActionEvent event) throws IOException {
        System.out.println("Abrir Caixa");
    }
    @FXML
    public void fecharCaixa(ActionEvent event) throws IOException {
        System.out.println("Fechar Caixa");
    }
    
    @FXML
    public void adicionarItemAVenda(ActionEvent event) throws IOException {
        //Quando eu clicar em adicionar Item a Venda preciso criar o item_venda
        //Preciso pegar os dados dos campos da View de Nova Venda e criar um novo item_venda, adicionar a uma lista de item_venda (local) e mostrar na tabela
        //Quando o usuário clicar em finalizar, pegar a lista local e adicionar a uma Venda associada.
        //ao finalizar adicionar ao ArrayList<> daquela Venda todos os itens_vendas criados
        if (cmbboxItem.getValue() != null) {
            Item_venda novo_item_venda = criarItemVenda();
            itensDaVenda.add(novo_item_venda);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um item para adicionar item a venda!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
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
    
    ItemDAO itemDAO = new ItemDAO();
    ObservableList<Item> itens = FXCollections.observableArrayList(itemDAO.todosOsItens());
    
    ClienteDAO clienteDAO = new ClienteDAO();
    ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Data do sistema no DataPicker
        dtpkrDataVenda.setValue(LocalDate.now());
        
        //Inicialização das TableColumn
        tbclnProdutoServico.setCellValueFactory(cellData -> 
        {
            return new SimpleStringProperty(cellData.getValue().getItem().getNome_item());
        });
        tbclnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tbclnValor.setCellValueFactory((TableColumn.CellDataFeatures<Item_venda, Double> cellData) -> new SimpleDoubleProperty(cellData.getValue().getValor_unitario()).asObject());
        
        //Centralizar
        centralizarTextoNaColuna(tbclnProdutoServico);
        centralizarTextoNaColuna(tbclnValor);
        centralizarTextoNaColuna(tbclnQuantidade);
        
        //Povoando os ComboBox
        cmbboxItem.setItems(itens);
        
        cmbboxItem.valueProperty().addListener((ObservableValue<? extends Item> observable, Item oldValue, Item newValue) -> {
            if (newValue != oldValue) {
                txtfldValorUnitario.setText(newValue.getValor_item().toString());
                Double preco_calculado = newValue.getQuantidade() * newValue.getValor_item();
                txtfldPreco.setText(preco_calculado.toString());
            } else {
                txtfldValorUnitario.setText(oldValue.getValor_item().toString());
                Double preco_calculado = oldValue.getQuantidade() * oldValue.getValor_item();
                txtfldPreco.setText(preco_calculado.toString());
            }
        });
        
        cmbboxCliente.setItems(clientes);
        
        
    }    

    private Item_venda criarItemVenda() {
        int quantidade = spnrQuantidade.getValue();
        Double valor_unitario = Double.valueOf(txtfldPreco.getText());
        Item item = cmbboxItem.getSelectionModel().getSelectedItem();
        Item_venda item_venda = new Item_venda(quantidade, valor_unitario, item);
        return item_venda;
    }
    
}
