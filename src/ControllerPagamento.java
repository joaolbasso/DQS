import DAO.CaixaDAO;
import DAO.ClienteDAO;
import DAO.ItemDAO;
import DAO.Item_caixaDAO;
import DAO.PagamentoDAO;
import DAO.ParcelaDAO;
import DAO.UsuarioDAO;
import DAO.VendaDAO;
import Model.Caixa;
import Model.Cliente;
import Model.Item;
import Model.Item_caixa;
import Model.Item_venda;
import Model.Pagamento;
import Model.Parcela;
import Model.Usuario;
import Model.Venda;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.ImageIcon;

public class ControllerPagamento implements Initializable {

    private Scene cenaAnterior;
    private Venda venda;
    private int index_cliente;
    
    
    @FXML
    private Label lblValorVenda;
    
    @FXML
    private RadioButton rdbtnAVista;
    
    @FXML
    private RadioButton rdbtnAPrazo;
    
    @FXML
    private ToggleGroup tipo_venda;
    
    @FXML
    private Label lblValorRecebidoOuEntrada;
    
    @FXML
    private TextField txtfldValorRecebido;
    
    @FXML
    private ComboBox<String> cmbboxMetodoPagamento;

    @FXML
    private Label lblSelecionarCliente;
    
    @FXML
    private ComboBox<Cliente> cmbboxCliente;
    
    @FXML
    private Button btnCadastrarCliente;
    
    @FXML
    private Label lblNumeroParcelas;
    
    @FXML
    private Spinner<Integer> spnrNumeroParcelas;
    
    @FXML
    private TableView<Parcela> tbvwParcelas;
    
    @FXML
    private TableColumn<Parcela, Integer> tbclnParcelaN = new TableColumn<>("Nº parcela");
    
    @FXML
    private TableColumn<Parcela, Double> tbclnValor = new TableColumn<>("Valor");
    
    @FXML
    private TableColumn<Parcela, LocalDate> tbclnVencimento = new TableColumn<>("Vencimento");

    @FXML
    private Button btnConcluir;
    
    @FXML
    private AnchorPane paneAPrazo;
    
    @FXML
    private Label lblValorRestanteTexto;
    
    @FXML
    private Label lblValorRestante;
    
    
    private ObservableList<Parcela> lista_parcelas_observable = FXCollections.observableArrayList();

    public ObservableList<Parcela> getLista_parcelas_observable() {
        return lista_parcelas_observable;
    }

    public void setLista_parcelas_observable(ObservableList<Parcela> lista_parcelas_observable) {
        this.lista_parcelas_observable = lista_parcelas_observable;
    }
    
    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
        
    }

    public int getIndex_cliente() {
        return index_cliente;
    }

    public void setIndex_cliente(int index_cliente) {
        this.index_cliente = index_cliente;
    }


    public Scene getCenaAnterior() {
        return cenaAnterior;
    }

    public Venda getVenda() {
        return venda;
    }
    
    public void setVenda(Venda venda) {
        this.venda = venda;
        atualizarValorVenda();
        txtfldValorRecebido.setText(this.venda.getValor_venda().toString());
        Parcela parcela = new Parcela(this.venda.getValor_venda() / 2, LocalDate.now().plusDays(28 * 1), this.venda, 1);
        lista_parcelas_observable.add(parcela);
        tbvwParcelas.setItems(lista_parcelas_observable);
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(this.cenaAnterior);
        window.show();
    }
    
    @FXML
    public void voltarConcluido(ActionEvent event) throws IOException {
        String nomeDaView = "Caixa.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
        Parent view = loader.load();

        Scene cena = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cena);
        window.show();
    }
    
    @FXML
    public void cadastrarCliente(ActionEvent event) throws IOException {
    // Carregar a nova tela
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

    
    
    @FXML
    public void concluirVenda(ActionEvent event) throws IOException, InterruptedException {
        if (cmbboxMetodoPagamento.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Escolha um método de pagamento!", "Método de pagamento", 0);
            return;
        }
        
        VendaDAO vendaDAO = new VendaDAO();
        ParcelaDAO parcelaDAO = new ParcelaDAO();
        PagamentoDAO pagamentoDAO = new PagamentoDAO();
        CaixaDAO caixaDAO = new CaixaDAO();
        Item_caixaDAO item_caixaDAO = new Item_caixaDAO();
        ItemDAO itemDAO = new ItemDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        char metodo_pagamento = cmbboxMetodoPagamento.getSelectionModel().getSelectedItem().charAt(0);
        
        Caixa caixaAtual = caixaDAO.buscarCaixaAberto();
        
        if (caixaAtual == null) {
            JOptionPane.showMessageDialog(null, "Não há um caixa existente aberto, um novo será criado", "Caixa novo criado", JOptionPane.INFORMATION_MESSAGE);
            Usuario usuario = usuarioDAO.selectUnico();
        
        final double LIMITE_MINIMO = 0.0;
        final double LIMITE_MAXIMO = 1000.0;
        
        ImageIcon icon = new ImageIcon("View/Icons/aporte.png");
        
        //
        String input;
        double valor = 0;
        boolean entradaValida = false;

        // Loop até obter uma entrada válida
        while (!entradaValida) {
            // Solicita a entrada do usuário
            input = (String) JOptionPane.showInputDialog(null,
                "Deseja fazer um aporte inicial no caixa?",
                "Aporte Inicial",
                JOptionPane.YES_NO_OPTION,
                icon,
                null,
                null
            );

            // Verifica se a entrada é nula (usuário clicou em Cancelar)
            if (input == null) {
                int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que não realizará um aporte inicial ao caixa? Isso não poderá ser feito posteriormente.", "Confirmação", 0, 0);
                if (resposta == 0) {
                    valor = 0.0;
                    break;
                } else {
                    continue;
                }
            }

            try {
                // Tenta converter a entrada para um número de ponto flutuante
                valor = Double.parseDouble(input);

                // Verifica se o valor está dentro dos limites
                if (valor >= LIMITE_MINIMO && valor <= LIMITE_MAXIMO) {
                    entradaValida = true; // Entrada válida, saia do loop
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Por favor, digite um número entre R$0.00 a R$1000.00.",
                        "Entrada Inválida",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException e) {
                // Se ocorrer uma exceção, a entrada não é um número válido
                JOptionPane.showMessageDialog(null,
                    "Por favor, digite um número ou cancele a operação.",
                    "Entrada Inválida",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        // Exibe o valor válido digitado
        //JOptionPane.showMessageDialog(null, "Você digitou: " + valor);
        
        caixaAtual = new Caixa(usuario);
        caixaDAO.insert(caixaAtual);
        Item_caixa item_aporte = new Item_caixa(valor, LocalDate.now(), Item_caixa.TipoOperacao.A, caixaAtual, 'A', "Aporte Inicial");
        
        caixaAtual.getItens_caixa().add(item_aporte);
        
        item_caixaDAO.insert(item_aporte);
        
        caixaDAO.update(caixaAtual);
        }

        if (cmbboxMetodoPagamento.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Selecione um método de pagamento!!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (Double.valueOf(txtfldValorRecebido.getText()) > this.venda.getValor_venda()) {
            JOptionPane.showMessageDialog(null, "Valor recebido não pode ser maior que valor da venda!!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (tipo_venda.getSelectedToggle() == rdbtnAVista) { //A vista
            if(Objects.equals(Double.valueOf(txtfldValorRecebido.getText()), this.venda.getValor_venda())) {
                this.venda.setCliente(cmbboxCliente.getSelectionModel().getSelectedItem());
                Parcela parcelaUnica = new Parcela(this.venda.getValor_venda(), this.venda);
                Pagamento pagamento = new Pagamento('C', metodo_pagamento, this.venda.getData_venda(), this.venda.getValor_venda(), parcelaUnica);
                
                StringBuilder lista_nome_itens_builder = new StringBuilder();
                
                for (Item_venda item : this.venda.getItens_venda()) {
                    Item itemEntity = item.getItem();
                    Item managedItem = itemDAO.update(itemEntity);
                    int id_ultimo = 0;
                        if (lista_nome_itens_builder.length() > 0) {
                            lista_nome_itens_builder.append(", ");
                        }
                        // Evite criar uma nova instância se já existe no contexto
                    // Utilize merge se necessário
                    if (id_ultimo != item.getItem().getId_item())
                        lista_nome_itens_builder.append(managedItem.getNome_item());
                    }

                String lista_nome_itens = lista_nome_itens_builder.toString();
                
                Item_caixa item_caixa = new Item_caixa(this.venda.getValor_venda(), pagamento.getData_pagamento(), lista_nome_itens, Item_caixa.TipoOperacao.V, metodo_pagamento, caixaAtual, pagamento);

                vendaDAO.insert(this.venda);
                parcelaDAO.insert(parcelaUnica);
                pagamentoDAO.insert(pagamento);
                
                caixaAtual.getItens_caixa().add(item_caixa);
                item_caixaDAO.insert(item_caixa);
                caixaDAO.update(caixaAtual);
                
                JOptionPane.showMessageDialog(null, "Venda a vista registrada com sucesso!", "Venda a vista com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                voltarConcluido(event);
            } 
        } else { // A prazo
                if (cmbboxCliente.getValue() == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente para venda a prazo!!", "Aviso!", JOptionPane.WARNING_MESSAGE);
                return;
                }
                
                this.venda.setCliente(cmbboxCliente.getSelectionModel().getSelectedItem());
                vendaDAO.insert(this.venda);
                Parcela parcelaEntrada = new Parcela(Double.valueOf(txtfldValorRecebido.getText()), this.venda);
                Pagamento pagamento = new Pagamento('C', metodo_pagamento, this.venda.getData_venda(), this.venda.getValor_venda(), parcelaEntrada);
                
                StringBuilder lista_nome_itens_builder = new StringBuilder();
                
                for (Item_venda item : this.venda.getItens_venda()) {
                    Item itemEntity = item.getItem();
                    Item managedItem = itemDAO.update(itemEntity);
                    int id_ultimo = 0;
                        if (lista_nome_itens_builder.length() > 0) {
                            lista_nome_itens_builder.append(", ");
                        }
                        // Evite criar uma nova instância se já existe no contexto
                    // Utilize merge se necessário
                    if (id_ultimo != item.getItem().getId_item())
                        lista_nome_itens_builder.append(managedItem.getNome_item());
                    }

                String lista_nome_itens = lista_nome_itens_builder.toString();
                
                Item_caixa item_caixa = new Item_caixa(this.venda.getValor_venda(), pagamento.getData_pagamento(), lista_nome_itens, Item_caixa.TipoOperacao.V, metodo_pagamento, caixaAtual, pagamento);

                parcelaDAO.insert(parcelaEntrada);
                pagamentoDAO.insert(pagamento);
                
                caixaAtual.getItens_caixa().add(item_caixa);
                item_caixaDAO.insert(item_caixa);
                caixaDAO.update(caixaAtual);
                
                for (Parcela parcela : lista_parcelas_observable) {
                    parcelaDAO.insert(parcela);
                    System.out.println("Insert parcela: " + parcela.getNumero_parcela());
                }
                
                JOptionPane.showMessageDialog(null, "Venda a prazo registrada com sucesso!", "Venda a prazo com sucesso!", JOptionPane.INFORMATION_MESSAGE);
                voltarConcluido(event);
        }
    }

    ObservableList<String> metodos_pagamento = FXCollections.observableArrayList("Espécie", "PIX", "Débito", "Crédito");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        criaSpinnerValueFactory();
        tbclnParcelaN.setCellValueFactory(new PropertyValueFactory<>("numero_parcela"));
        tbclnValor.setCellValueFactory(new PropertyValueFactory<>("valor_parcela"));
        tbclnVencimento.setCellValueFactory(new PropertyValueFactory<>("data_vencimento"));
        
        spnrNumeroParcelas.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
                atualizaParcelas();
        });
        
        txtfldValorRecebido.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizaParcelas();
    });
        
        paneAPrazo.setVisible(false);
        
        atualizaComboBoxCliente();
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
        
        lblValorRecebidoOuEntrada.setText("Valor recebido:");
        spnrNumeroParcelas.setOpacity(0.5);
        spnrNumeroParcelas.setDisable(true);
        
        cmbboxMetodoPagamento.setItems(metodos_pagamento);
        cmbboxMetodoPagamento.getSelectionModel().select(0);
        
        tipo_venda.selectedToggleProperty().addListener((ObservableValue<? extends javafx.scene.control.Toggle> observable, javafx.scene.control.Toggle oldValue, javafx.scene.control.Toggle newValue) -> {
            RadioButton selectedRadioButton = (RadioButton) newValue;
            if(selectedRadioButton == rdbtnAVista) {
                txtfldValorRecebido.setText(this.venda.getValor_venda().toString());
                lblValorRecebidoOuEntrada.setText("Valor recebido:");
                spnrNumeroParcelas.setOpacity(0.5);
                spnrNumeroParcelas.setDisable(true);
                paneAPrazo.setVisible(false);
            } else {
                cmbboxCliente.getSelectionModel().select(getIndex_cliente());
                spnrNumeroParcelas.setOpacity(1);
                spnrNumeroParcelas.setDisable(false);
                lblValorRecebidoOuEntrada.setText("Valor de entrada:");
                paneAPrazo.setVisible(true);
                Double valorMetade = this.venda.getValor_venda() / 2;
                txtfldValorRecebido.setText(valorMetade.toString());
            }                
        });
        
        
    }    

    private void atualizarValorVenda() {
        if (venda != null) {
            lblValorVenda.setText(String.format("R$ %.2f", venda.getValor_venda()));
        }
    }
    
    public void criaSpinnerValueFactory() {
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
        
        valueFactory.setValue(1);
        spnrNumeroParcelas.setValueFactory(valueFactory);
    }
    
    public void atualizaComboBoxCliente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(clienteDAO.todosOsClientes());
        cmbboxCliente.setItems(clientes);
    }

    private void atualizaParcelas() {
        Integer numero_parcelas = spnrNumeroParcelas.getValue();
                Double valor_restante = this.venda.getValor_venda() - Double.valueOf(txtfldValorRecebido.getText());
                lblValorRestante.setText(valor_restante.toString());
                Double valor_cada_parcela_todo = valor_restante / numero_parcelas;
                BigDecimal bd = new BigDecimal(valor_cada_parcela_todo).setScale(2, RoundingMode.HALF_UP);
                double valor_cada_parcela = bd.doubleValue();
                lista_parcelas_observable.clear();
                for(int i = 1; i <= numero_parcelas; i++) {
                    Parcela parcela = new Parcela(valor_cada_parcela, LocalDate.now().plusDays(28 * i), this.venda, i);
                    lista_parcelas_observable.add(parcela);
                }
            tbvwParcelas.setItems(lista_parcelas_observable);
    }
    
    
    
        
    

    
}