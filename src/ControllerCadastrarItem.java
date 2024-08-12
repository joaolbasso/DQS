import DAO.ItemDAO;
import Model.Item;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ControllerCadastrarItem implements Initializable {
    
    @FXML
    private TextField txtfldNomeItem;
    
    @FXML
    private TextField txtfldPrecoCusto;
    
    @FXML
    private TextField txtfldPrecoVenda;
    
    @FXML
    private DatePicker dtpckrDataCompra;
    
    @FXML
    private Spinner<Integer> spnrQuantidade;
    
    @FXML
    private TextField txtfldDescricao;
    
    @FXML
    private RadioButton rdbtnProduto, rdbtnServico;
    
    @FXML
    private ToggleGroup tipoItem;
    
    public void voltarParaItens(ActionEvent event) throws IOException {
        Parent itemView = FXMLLoader.load(getClass().getResource("/View/Item.fxml"));
        Scene itemScene = new Scene(itemView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(itemScene);
        window.show();
    }
    
    public void cadastrarItem(ActionEvent event) throws IOException {
        //Ver sobre clicar no botão com campos vazios, fazer os campos não receberem caracteres inválidos
        Item itemSalvar = criarItem();
        ItemDAO item = new ItemDAO();
        item.insert(itemSalvar);
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        //Você tem certeza que deseja limpar os campos? CRIAR POPUP COM SIM NAO E ESSA PERGUNTA
        int resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja limpar os campos?", "Limpar Campos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            txtfldNomeItem.setText("");
            txtfldPrecoCusto.setText("");
            txtfldPrecoVenda.setText("");
            txtfldDescricao.setText("");
            criaSpinnerValueFactory();
            dtpckrDataCompra.setValue(LocalDate.now());
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dtpckrDataCompra.setValue(LocalDate.now());
        criaSpinnerValueFactory();
    } 
    
    public void criaSpinnerValueFactory() {
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999);
        
        valueFactory.setValue(1);
        spnrQuantidade.setValueFactory(valueFactory);
    }

    private char checkSelectedRadioButton() {
        RadioButton selectedRadioButton = (RadioButton) tipoItem.getSelectedToggle();
        char tipo_item = selectedRadioButton.getText().charAt(0);
        return tipo_item;
    } 
    
    private Item criarItem() {
        String nome_item = txtfldNomeItem.getText();
        Double preco_custo = Double.valueOf(txtfldPrecoCusto.getText());
        Double preco_venda = Double.valueOf(txtfldPrecoVenda.getText());
        LocalDate data_preco_item = dtpckrDataCompra.getValue();
        int quantidade = spnrQuantidade.getValue();
        char tipo_item = checkSelectedRadioButton();
        String descricao_item = txtfldDescricao.getText();
        
        return new Item(nome_item, preco_custo, preco_venda, data_preco_item, quantidade, tipo_item, descricao_item);
    }
    
}
