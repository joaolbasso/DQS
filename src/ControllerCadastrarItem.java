import DAO.ItemDAO;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ControllerCadastrarItem implements Initializable {

    private Item itemEdicao;
    private Scene cenaAnterior;
    
    @FXML
    private Label txtTitulo;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000);
        valueFactory.setValue(0);
        spnrQuantidade.setValueFactory(valueFactory);
        
        if (itemEdicao != null) {
            txtTitulo.setText("Editar Item");
        }
    }

    public Scene getCenaAnterior() {
        return cenaAnterior;
    }

    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }
    
    @FXML
    public void voltar(ActionEvent event) throws IOException {
        String nomeDaView;
        if (this.cenaAnterior == null) {
             nomeDaView = "Item.fxml";
        } else {
            nomeDaView = "Caixa.fxml";
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
        Parent view = loader.load();

        Scene cena = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cena);
        window.show();
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        
       txtfldNomeItem.setText("");
        txtfldPrecoCusto.setText("");
        txtfldPrecoVenda.setText("");
        txtfldDescricao.setText("");
        spnrQuantidade.getValueFactory().setValue(0);
        dtpckrDataCompra.setValue(LocalDate.now());
        tipoItem.selectToggle(null); // Deselect radio buttons
    }

    public void setItem(Item item) {
        this.itemEdicao = item;
        if (item != null) {
            txtfldNomeItem.setText(item.getNome_item());
            txtfldPrecoCusto.setText(String.valueOf(item.getPreco_custo_item()));
            txtfldPrecoVenda.setText(String.valueOf(item.getValor_item()));
            dtpckrDataCompra.setValue(item.getData_preco_item());
            spnrQuantidade.getValueFactory().setValue(item.getQuantidade());
            txtfldDescricao.setText(item.getDescricao_item());

            if (item.getTipo_item() == 'P') {
                rdbtnProduto.setSelected(true);
            } else if (item.getTipo_item() == 'S') {
                rdbtnServico.setSelected(true);
            }
        }
    }

    public void cadastrarItem(ActionEvent event) throws IOException {
        try {
            Item itemSalvar = criarItem();
            if (itemSalvar != null) {
                ItemDAO itemDAO = new ItemDAO();
                if (itemEdicao == null) {
                    // Novo item
                    itemDAO.insert(itemSalvar);
                    JOptionPane.showMessageDialog(null, "Item cadastrado com sucesso!");
                } else {
                    // Editar item existente
                    itemSalvar.setId_item(itemEdicao.getId_item());
                    itemDAO.update(itemSalvar);
                    JOptionPane.showMessageDialog(null, "Item editado com sucesso!");
                    voltar(event);
                }
                limparCampos(event);
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir o Item!", "Erro ao inserir", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar o Item.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private char checkSelectedRadioButton() {
        RadioButton selectedRadioButton = (RadioButton) tipoItem.getSelectedToggle();
        return selectedRadioButton.getText().charAt(0);
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
