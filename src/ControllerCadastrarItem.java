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
        /*
        System.out.println(itemSalvar.getNome_item());
        System.out.println(itemSalvar.getPreco_custo_item());
        System.out.println(itemSalvar.getValor_item());
        System.out.println(itemSalvar.getData_preco_item());
        System.out.println(itemSalvar.getQuantidade());
        System.out.println(itemSalvar.getTipo_item());
        System.out.println(itemSalvar.getDescricao_item());
        */
        Item itemSalvar = criarItem();
        ItemDAO item = new ItemDAO();
        item.insert(itemSalvar);
        System.out.println("CADASTRADO");
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        System.out.println("LIMPOU");
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30);
        
        valueFactory.setValue(0);
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
