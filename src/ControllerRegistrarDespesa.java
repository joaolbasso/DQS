import DAO.DespesaDAO;
import Model.Beneficiario;
import Model.Despesa;
import Model.Item;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerRegistrarDespesa implements Initializable {
    
    @FXML
    private TextField txtfldNomeDespesa;
    @FXML
    private TextField txtfldValor;
    @FXML
    private Spinner<Integer> spnrRecorrente;
    @FXML
    private RadioButton rbtnPago, rbtnASerPago;
    @FXML
    private ComboBox<String> cmbboxBeneficiario;
    private String[] beneficiarios = {"LUZ", "AGUA", "INTERNET"}; //Essa collection virá do Banco de DADOS
    @FXML
    private Button btnCadastrarBeneficiario;
    @FXML
    private DatePicker dtpkrDataPagamento;
    @FXML
    private DatePicker dtpkrDataVencimento;
    @FXML
    private TextField txtfldDescricao;
    
    public void voltarParaDespesa(ActionEvent event) throws IOException {
        Parent despesaView = FXMLLoader.load(getClass().getResource("/View/Despesa.fxml"));
        Scene despesaScene = new Scene(despesaView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(despesaScene);
        window.show();
    }
    
    public void entrarCadastrarBeneficiario(ActionEvent event) throws IOException {
        Parent despesaView = FXMLLoader.load(getClass().getResource("/View/CadastrarBeneficiario.fxml"));
        Scene despesaScene = new Scene(despesaView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(despesaScene);
        window.show();
    }
    
    public void registrarDespesa(ActionEvent event) throws IOException {
        Despesa despesaSalvar = criarDespesa();
        DespesaDAO despesa = new DespesaDAO();
        despesa.insert(despesaSalvar);
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        txtfldNomeDespesa.setText("");
        txtfldValor.setText("");
        txtfldDescricao.setText("");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30);
        
        valueFactory.setValue(0);
        spnrRecorrente.setValueFactory(valueFactory);
        
        
        
        cmbboxBeneficiario.getItems().addAll(beneficiarios);
    }    

    private Despesa criarDespesa() {
        String nome_despesa = txtfldNomeDespesa.getText();
        Double valor_despesa = Double.valueOf(txtfldValor.getText());
        int recorrencia_despesa = spnrRecorrente.getValue();
        LocalDate data_pagamento_despesa = dtpkrDataPagamento.getValue();
        LocalDate data_vencimento_despesa = dtpkrDataVencimento.getValue();
        String descricao_despesa = txtfldDescricao.getText();
        
        /*Beneficiario beneficiario = cmbboxBeneficiario.();*/
        

        
        return new Despesa(nome_despesa, valor_despesa, descricao_despesa, recorrencia_despesa, data_vencimento_despesa, data_pagamento_despesa);
    }
    
}
