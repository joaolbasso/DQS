import java.io.IOException;
import java.net.URL;
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
        System.out.println("Nome: " + txtfldNomeDespesa.getText());
        System.out.println("Valor: " + txtfldValor.getText());
        System.out.println("Recorrente: " + spnrRecorrente.getValue());
        System.out.println("Beneficiário: " + cmbboxBeneficiario.getSelectionModel().getSelectedItem());
        System.out.println("Data Pagamento: " + dtpkrDataPagamento.getValue());
        System.out.println("Data Vencimento: " + dtpkrDataVencimento.getValue());
        System.out.println("Descricao: " + txtfldDescricao.getText());
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        System.out.println("LIMPOU CAMPOS");
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
    
}
