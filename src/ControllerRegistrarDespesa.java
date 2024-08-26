import DAO.BeneficiarioDAO;
import DAO.DespesaDAO;
import Model.Beneficiario;
import Model.Despesa;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ControllerRegistrarDespesa implements Initializable {

    private Scene cenaAnterior;
    private Despesa despesaEdicao;

    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    // Método para definir a despesa que será editada
    public void setDespesa(Despesa despesa) {
        this.despesaEdicao = despesa;
        if (despesa != null) {
            txtfldNomeDespesa.setText(despesa.getNome_despesa());
            txtfldValor.setText(despesa.getValor_despesa().toString());
            spnrRecorrente.getValueFactory().setValue(despesa.getRecorrencia_despesa());
            dtpkrDataPagamento.setValue(despesa.getData_pagamento_despesa());
            dtpkrDataVencimento.setValue(despesa.getData_vencimento_despesa());
            txtfldDescricao.setText(despesa.getDescricao_despesa());
            cmbboxBeneficiario.getSelectionModel().select(despesa.getBeneficiario());
        }
    }

   @FXML
    public void voltar(ActionEvent event) throws IOException {
        String nomeDaView = "Despesa.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
        Parent view = loader.load();

        Scene cena = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cena);
        window.show();
    }

    
    @FXML
    private TextField txtfldNomeDespesa;
    @FXML
    private TextField txtfldValor;
    @FXML
    private Spinner<Integer> spnrRecorrente;
    @FXML
    private ComboBox<Beneficiario> cmbboxBeneficiario;
    @FXML
    private Button btnCadastrarBeneficiario;
    @FXML
    private DatePicker dtpkrDataPagamento;
    @FXML
    private DatePicker dtpkrDataVencimento;
    @FXML
    private TextField txtfldDescricao;
    
    public void entrarCadastrarBeneficiario(ActionEvent event) throws IOException {
        // Carregar a nova tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarBeneficiario.fxml"));
        Parent cadastrarBeneficiarioView = loader.load();

        // Obter o controlador da nova tela
        ControllerCadastrarBeneficiario controllerCadastrarBeneficiario = loader.getController();

        // Definir a cena atual como a anterior no controlador da nova tela
        controllerCadastrarBeneficiario.setCenaAnterior(((Node) event.getSource()).getScene());

        // Mudar para a nova cena
        Scene cadastrarBeneficiarioScene = new Scene(cadastrarBeneficiarioView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cadastrarBeneficiarioScene);
        window.show();
    }

    @FXML
    public void registrarDespesa(ActionEvent event) {
        try {
            Despesa despesaSalvar = criarDespesa();
            if (despesaSalvar != null) {
                DespesaDAO despesaDAO = new DespesaDAO();
                if (despesaEdicao == null) {
                    // Nova despesa
                    despesaDAO.insert(despesaSalvar);
                } else {
                    // Editar despesa existente
                    despesaSalvar.setId_despesa(despesaEdicao.getId_despesa());
                    despesaDAO.update(despesaSalvar);
                }
                limparCampos(event); // Limpa os campos após o registro
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir a DESPESA!", "Erro ao inserir", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Exibir mensagem de erro ao usuário
        }
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        txtfldNomeDespesa.setText("");
        txtfldValor.setText("");
        txtfldDescricao.setText("");
        dtpkrDataPagamento.setValue(null);
        dtpkrDataVencimento.setValue(null);
        cmbboxBeneficiario.getSelectionModel().clearSelection();
        spnrRecorrente.getValueFactory().setValue(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30);
        valueFactory.setValue(0);
        spnrRecorrente.setValueFactory(valueFactory);
        
        BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
        
        ObservableList<Beneficiario> beneficiarios = FXCollections.observableArrayList(beneficiarioDAO.todosOsBeneficiarios());
        cmbboxBeneficiario.setItems(beneficiarios);
        
        cmbboxBeneficiario.setCellFactory(cell -> new ListCell<Beneficiario>() {
            protected void updateItem(Beneficiario beneficiario, boolean empty) {
                super.updateItem(beneficiario, empty);
                if (empty || beneficiario == null) {
                    setText(null);
                } else {
                    setText(beneficiario.getNome_beneficiario());
                }
            }
        });
        
        cmbboxBeneficiario.setButtonCell(cmbboxBeneficiario.getCellFactory().call(null));
    }    

    private Despesa criarDespesa() {
        try {
            String nome_despesa = txtfldNomeDespesa.getText();
            Double valor_despesa = Double.valueOf(txtfldValor.getText());
            int recorrencia_despesa = spnrRecorrente.getValue();
            LocalDate data_pagamento_despesa = dtpkrDataPagamento.getValue();
            LocalDate data_vencimento_despesa = dtpkrDataVencimento.getValue();
            String descricao_despesa = txtfldDescricao.getText();
            Beneficiario beneficiario = cmbboxBeneficiario.getSelectionModel().getSelectedItem();
            

            if (nome_despesa.isEmpty() || valor_despesa.isNaN()) {
                return null;
            }

            return new Despesa(nome_despesa, valor_despesa, descricao_despesa, recorrencia_despesa, data_vencimento_despesa, data_pagamento_despesa, beneficiario);
        } catch (NumberFormatException e) {
            // Tratar caso o valor da despesa não seja um número válido.
            e.printStackTrace();
            return null;
        }
    }
}
