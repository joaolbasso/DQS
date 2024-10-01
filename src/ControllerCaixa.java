import DAO.ClienteDAO;
import DAO.ItemDAO;
import Model.Cliente;
import Model.Item;
import Model.Item_venda;
import Model.Venda;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
    private ComboBox<Cliente> cmbboxCliente;
    
    @FXML
    private Spinner<Integer> spnrQuantidade;
    @FXML
    private TextField txtfldValorUnitario;
    @FXML
    private TextField txtfldPreco;
    @FXML
    private TextField txtfldDesconto;
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
    @FXML
    private TableColumn<Item_venda, Void> tbclnAcoes = new TableColumn<>("Ações");
    
    
    private ArrayList<Item_venda> itensDaVenda = new ArrayList<>();
    private Double valor_venda = 0.0;

    public ArrayList<Item_venda> getItensDaVenda() {
        return itensDaVenda;
    }

    public void setItensDaVenda(ArrayList<Item_venda> itensDaVenda) {
        this.itensDaVenda = itensDaVenda;
    }

    public Double getValor_venda() {
        return valor_venda;
    }

    public void setValor_venda(Double valor_venda) {
        this.valor_venda = valor_venda;
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
    public void cadastrarItem(ActionEvent event) throws IOException {
        // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarItem.fxml"));
        Parent caixaView = loader.load();

        // Obter o controller da nova tela
        ControllerCadastrarItem controllerCadastrarItem = loader.getController();
        
        controllerCadastrarItem.setItemCallBack(() -> {
        // Atualizar a ComboBox
        atualizaComboBoxItem();
        });

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
        //Quando o usuário clicar em finalizar, pegar a lista local e adicionar a uma Venda associada.
        //ao finalizar adicionar ao ArrayList<> daquela Venda todos os itens_vendas criados
        
        LocalDate data = dtpkrDataVenda.getValue();
        
        if (data == null || data.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Não é possível atribuir data futura!!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            dtpkrDataVenda.setValue(LocalDate.now());
            return;
        }
        
        if (data.isBefore(LocalDate.now().minusDays(5))) {
            JOptionPane.showMessageDialog(null, "Não é possível atribuir data anterior a 5 dias!!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            dtpkrDataVenda.setValue(LocalDate.now());
            return;
        }
        
        if (itensDaVenda.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adicione itens à venda antes de finalizar!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Venda venda = new Venda(getValor_venda(), data);
        venda.setItens_venda(itensDaVenda);
        
        // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Pagamento.fxml"));
        Parent caixaView = loader.load();

        // Obter o controller da nova tela
        ControllerPagamento controllerPagamento = loader.getController();

        // Definir a cena atual como a anterior no controller da nova tela
        controllerPagamento.setCenaAnterior(((Node) event.getSource()).getScene());
        controllerPagamento.setVenda(venda);
        controllerPagamento.setIndex_cliente(cmbboxCliente.getSelectionModel().getSelectedIndex());
        
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
    // Verifica se algum item está selecionado
    if (cmbboxItem.getValue() != null) {

        Item_venda novo_item_venda = criarItemVenda();  // Cria uma instância de Item_venda

        // Validação para garantir que o campo de preço tenha um valor válido
        if (!txtfldPreco.getText().isEmpty()) {
            try {
                Double preco_alterado = Double.valueOf(txtfldPreco.getText());

                // Verificação e aplicação do desconto
                if (!txtfldDesconto.getText().isEmpty()) {
                    try {
                        Double desconto = Double.valueOf(txtfldDesconto.getText());
                        // Certifique-se de que o desconto não exceda o preço
                        if (desconto > preco_alterado) {
                            JOptionPane.showMessageDialog(null, "O desconto não pode ser maior que o preço!", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        preco_alterado -= desconto;  // Aplica o desconto ao preço
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Insira um valor de desconto válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Define o valor unitário ajustado com desconto
                novo_item_venda.setValor_unitario_com_desconto(preco_alterado / novo_item_venda.getQuantidade());

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Insira um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Atualiza o valor total somando os itens já presentes na venda
        valor_venda += novo_item_venda.getValor_unitario();
        itensDaVenda.add(novo_item_venda);

        // Atualiza a tabela e o total
        atualizaTabelaItens();
    } else {
        JOptionPane.showMessageDialog(null, "Selecione um item para adicionar item a venda!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    limparCampos();
}

    
    // Atualizar a exibição da tabela e recalcular o valor total
    private void atualizaTabelaItens() {
        ObservableList<Item_venda> itens_venda_observable = FXCollections.observableArrayList(itensDaVenda);
        tbvwItensVenda.setItems(itens_venda_observable);

        // Recalcula o valor total
        valor_venda = itensDaVenda.stream().mapToDouble(Item_venda::getValor_unitario).sum();
        lblValorTotal.setText("R$ " + String.valueOf(valor_venda) + "0");
    }

    // Método para garantir que o campo de preço aceite apenas números
    private void configurarCampoPreco() {
        txtfldPreco.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {  // Permite números e ponto decimal
                txtfldPreco.setText(newValue.replaceAll("[^\\d.]", ""));  // Remove caracteres não numéricos
            }
        });
    }
    
    // Método para garantir que o campo de preço aceite apenas números
    private void configurarCampoDesconto() {
        txtfldDesconto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {  // Permite números e ponto decimal
                txtfldDesconto.setText(newValue.replaceAll("[^\\d.]", ""));  // Remove caracteres não numéricos
            }
        });
    }
    
    @FXML
    public void cadastrarCliente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarCliente.fxml"));
        Parent caixaView = loader.load();

        // Obter o controller da nova tela
        ControllerCadastrarCliente controllerCadastrarCliente = loader.getController();
        
        controllerCadastrarCliente.setItemCallBack(() -> {
        // Atualizar a ComboBox
        atualizaComboBoxCliente();
        });

        // Definir a cena atual como a anterior no controller da nova tela
        controllerCadastrarCliente.setCenaAnterior(((Node) event.getSource()).getScene());

        // Mudar para a nova cena
        Scene caixaScene = new Scene(caixaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
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
        alinharTextoNaColuna(tbclnProdutoServico, "CENTER-LEFT");
        formatarMoedaNaColuna(tbclnValor);
        alinharTextoNaColuna(tbclnQuantidade, "CENTER-RIGHT");
        
        //Povoando os ComboBox
        atualizaComboBoxItem();
        cmbboxItem.valueProperty().addListener((ObservableValue<? extends Item> observable, Item oldValue, Item newValue) -> {
            txtfldValorUnitario.setText(newValue.getValor_item().toString());
            Double preco_calculado = spnrQuantidade.getValue() * newValue.getValor_item();
            txtfldPreco.setText(preco_calculado.toString());            
        });
        
        criaSpinnerValueFactory();
        spnrQuantidade.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            Double preco_calculado = spnrQuantidade.getValue() * cmbboxItem.getSelectionModel().getSelectedItem().getValor_item();
            txtfldPreco.setText(preco_calculado.toString());
        });
        
        atualizaComboBoxCliente();
        cmbboxItem.setCellFactory(cell -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome_item());
                }
            }
        });
        
        cmbboxItem.setButtonCell(cmbboxItem.getCellFactory().call(null));
        
        cmbboxCliente.setCellFactory(cell -> new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente cliente, boolean empty) {
                super.updateItem(cliente, empty);
                if (empty || cliente == null) {
                    setText(null);
                } else {
                    setText(cliente.getNome_cliente());
                }
            }
        });
        
        cmbboxCliente.setButtonCell(cmbboxCliente.getCellFactory().call(null));
        
        configurarCampoPreco();
        configurarCampoDesconto();
        
        tbclnProdutoServico.setCellValueFactory(cellData -> 
        {
            return new SimpleStringProperty(cellData.getValue().getItem().getNome_item());
        });
        tbclnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tbclnValor.setCellValueFactory((TableColumn.CellDataFeatures<Item_venda, Double> cellData) -> 
            new SimpleDoubleProperty(cellData.getValue().getValor_unitario()).asObject());

        // Centralizar as colunas
        alinharTextoNaColuna(tbclnProdutoServico, "CENTER-LEFT");
        formatarMoedaNaColuna(tbclnValor);
        alinharTextoNaColuna(tbclnQuantidade, "CENTER-RIGHT");

        // Configurar os botões de editar e remover
        adicionarColunaAcoes();

        // Atualiza combo boxes e outros campos
        atualizaComboBoxItem();
        atualizaComboBoxCliente();
        
    }    
    
    public void criaSpinnerValueFactory() {
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999);
        
        valueFactory.setValue(1);
        spnrQuantidade.setValueFactory(valueFactory);
    }
    
    private Item_venda criarItemVenda() {
        int quantidade = spnrQuantidade.getValue();
        Double valor_item_venda = Double.valueOf(txtfldPreco.getText());
        Item item = cmbboxItem.getSelectionModel().getSelectedItem();
        Item_venda item_venda = new Item_venda(quantidade, valor_item_venda, item);
        return item_venda;
    }

    public void atualizaComboBoxItem() {
        ItemDAO itemDAO = new ItemDAO();
        ObservableList<Item> itens = FXCollections.observableArrayList(itemDAO.todosOsItens());
        cmbboxItem.setItems(itens);
    }
    
    public void atualizaComboBoxCliente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());
        cmbboxCliente.setItems(clientes);
    }

    private void limparCampos() {
        cmbboxItem.getSelectionModel().clearSelection();
        txtfldValorUnitario.setText("");
        txtfldPreco.setText("");
        txtfldDesconto.setText("");
        criaSpinnerValueFactory();
    }
    
    // Método para adicionar a coluna de botões
    private void adicionarColunaAcoes() {
        Callback<TableColumn<Item_venda, Void>, TableCell<Item_venda, Void>> cellFactory = new Callback<TableColumn<Item_venda, Void>, TableCell<Item_venda, Void>>() {
            @Override
            public TableCell<Item_venda, Void> call(final TableColumn<Item_venda, Void> param) {
                final TableCell<Item_venda, Void> cell = new TableCell<Item_venda, Void>() {

                    private final Button btnEditar = new Button("Editar");
                    private final Button btnRemover = new Button("Remover");

                    {
                        // Configurar o botão "Remover"
                        btnRemover.setOnAction((ActionEvent event) -> {
                            Item_venda item = getTableView().getItems().get(getIndex());
                            removerItem(item);
                        });

                        // Configurar o botão "Editar"
                        btnEditar.setOnAction((ActionEvent event) -> {
                            Item_venda item = getTableView().getItems().get(getIndex());
                            editarItem(item);
                        });
                    }

                    // Criar uma HBox com os botões para exibir na célula
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(10, btnEditar, btnRemover));
                        }
                    }
                };
                return cell;
            }
        };

        tbclnAcoes.setCellFactory(cellFactory);
        tbvwItensVenda.getColumns().add(tbclnAcoes);
    }

    // Método para remover o item da lista local e atualizar a tabela
    private void removerItem(Item_venda item) {
        itensDaVenda.remove(item);  // Remove da lista local
        atualizaTabelaItens();  // Recalcula o valor total e atualiza a tabela
    }

    // Método para editar o item selecionado
    private void editarItem(Item_venda item) {
        // Preencher os campos com os valores do item selecionado
        cmbboxItem.getSelectionModel().select(item.getItem());
        spnrQuantidade.getValueFactory().setValue(item.getQuantidade());
        txtfldValorUnitario.setText(item.getItem().getValor_item().toString());
        txtfldPreco.setText(String.valueOf(item.getValor_unitario()));

        // Remover o item da lista temporariamente para permitir edição
        itensDaVenda.remove(item);
        tbvwItensVenda.setItems(FXCollections.observableArrayList(itensDaVenda)); // Atualiza a tabela
    }
    
}