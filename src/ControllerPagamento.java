import DAO.ClienteDAO;
import DAO.PagamentoDAO;
import DAO.ParcelaDAO;
import DAO.VendaDAO;
import Model.Cliente;
import Model.Pagamento;
import Model.Parcela;
import Model.Venda;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.layout.AnchorPane;

public class ControllerPagamento implements Initializable {

    private Scene cenaAnterior;
    private Venda venda;
    
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
    private ArrayList<Parcela> lista_parcelas;

    public ArrayList<Parcela> getLista_parcelas() {
        return lista_parcelas;
    }

    public void setLista_parcelas(ArrayList<Parcela> lista_parcelas) {
        this.lista_parcelas = lista_parcelas;
    }
    
    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
        
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
    }

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
    public void cadastrarCliente(ActionEvent event) throws IOException {
    // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarCliente.fxml"));
        Parent caixaView = loader.load();

        // Obter o controller da nova tela
        ControllerCadastrarCliente controllerCadastrarCliente = loader.getController();

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
        if (tipo_venda.getSelectedToggle() == rdbtnAVista) {
            //A vista
        if (Double.valueOf(txtfldValorRecebido.getText()) > this.venda.getValor_venda()) {
            JOptionPane.showMessageDialog(null, "Valor recebido não pode ser maior que valor da venda!!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (cmbboxMetodoPagamento.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Selecione um método de pagamento!!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(Objects.equals(Double.valueOf(txtfldValorRecebido.getText()), this.venda.getValor_venda())) {
            char metodo_pagamento = cmbboxMetodoPagamento.getSelectionModel().getSelectedItem().charAt(0);
            Parcela parcelaUnica = new Parcela(this.venda.getValor_venda(), this.venda);
            Pagamento pagamento = new Pagamento('C',metodo_pagamento, this.venda.getData_venda(), this.venda.getValor_venda(), parcelaUnica);

            VendaDAO vendaDAO = new VendaDAO();
            vendaDAO.insertVendaAVista(this.venda);
            
            
            ParcelaDAO parcelaDAO = new ParcelaDAO();
            parcelaDAO.insert(parcelaUnica);
            
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            pagamentoDAO.insert(pagamento);
            
            
            JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!", "Venda com sucesso!", JOptionPane.INFORMATION_MESSAGE);
            voltar(event);
        } 
        /*
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PagamentoParcelado.fxml"));
            Parent pagamentoView = loader.load();
        
            // Obter o controlador da nova tela
            ControllerPagamentoParcelado controller = loader.getController();
        
            // Passar a cena atual para o controlador da nova tela
            controller.setCenaAnterior(((Node)event.getSource()).getScene());
//            //Passando venda para o outro controller
            controller.setVenda(this.venda);
            
            Scene pagamentoScene = new Scene(pagamentoView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(pagamentoScene);
            window.show();
        }
        */
        } else {
            //A prazo
        }
    }

    ObservableList<String> metodos_pagamento = FXCollections.observableArrayList("Espécie", "PIX", "Débito", "Crédito");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        
        tipo_venda.selectedToggleProperty().addListener((ObservableValue<? extends javafx.scene.control.Toggle> observable, javafx.scene.control.Toggle oldValue, javafx.scene.control.Toggle newValue) -> {
            RadioButton selectedRadioButton = (RadioButton) newValue;
            if(selectedRadioButton == rdbtnAVista) {
                txtfldValorRecebido.setText(this.venda.getValor_venda().toString());
                lblValorRecebidoOuEntrada.setText("Valor recebido:");
                spnrNumeroParcelas.setOpacity(0.5);
                spnrNumeroParcelas.setDisable(true);
                paneAPrazo.setVisible(false);
            } else {
                spnrNumeroParcelas.setOpacity(1);
                spnrNumeroParcelas.setDisable(false);
                lblValorRecebidoOuEntrada.setText("Valor de entrada:");
                paneAPrazo.setVisible(true);
                Double valorMetade = this.venda.getValor_venda() / 2;
                
                txtfldValorRecebido.setText(valorMetade.toString());
            }                
        });
        criaSpinnerValueFactory();
        spnrNumeroParcelas.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            Integer numero_parcelas = spnrNumeroParcelas.getValue();
            Double valor_restante = this.venda.getValor_venda() - Double.valueOf(txtfldValorRecebido.getText());
            Double valor_cada_parcela_todo = valor_restante / numero_parcelas;
            
            BigDecimal bd = new BigDecimal(valor_cada_parcela_todo).setScale(2, RoundingMode.HALF_UP);
            double valor_cada_parcela = bd.doubleValue();
            
            for(int i = 1; i <= numero_parcelas; i++) {
                Parcela parcela = new Parcela(valor_cada_parcela, LocalDate.now().plusDays(28 * i), this.venda, i);
                lista_parcelas.add(parcela);
                
            }
            
            ObservableList<Parcela> lista_parcelas_observable = FXCollections.observableArrayList(lista_parcelas);
            tbvwParcelas.setItems(lista_parcelas_observable);
            
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
    

    
}

