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
        
        dtpckrDataCompra.setValue(LocalDate.now());
        
        
        if (itemEdicao != null) {
            txtTitulo.setText("Editar Item");
        }
        
        txtfldPrecoCusto.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números e uma vírgula ou ponto para decimais
            if (!newValue.matches("\\d*([\\.,]\\d{0,2})?")) {
                txtfldPrecoCusto.setText(oldValue);
            } else {
                // Substituir vírgula por ponto para manter o formato decimal correto
                txtfldPrecoCusto.setText(newValue.replace(",", "."));
            }
        });
        
        txtfldPrecoVenda.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números e uma vírgula ou ponto para decimais
            if (!newValue.matches("\\d*([\\.,]\\d{0,2})?")) {
                txtfldPrecoVenda.setText(oldValue);
            } else {
                // Substituir vírgula por ponto para manter o formato decimal correto
                txtfldPrecoVenda.setText(newValue.replace(",", "."));
            }
        });
        
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
        tipoItem.selectToggle(rdbtnProduto);
    }

    public void setItem(Item item) {
        this.itemEdicao = item;
        if (item != null) {
            txtTitulo.setText("Editar Item");
            txtfldNomeItem.setText(item.getNome_item());
            
            txtfldPrecoCusto.setText(item.getPreco_custo_item().toString());
            txtfldPrecoVenda.setText(item.getValor_item().toString());
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
        try {
        String nome_item = txtfldNomeItem.getText();
            Double preco_custo;
            if (txtfldPrecoCusto.getText().isEmpty()) {
                preco_custo = 0.0;
            } else {
                preco_custo = Double.valueOf(txtfldPrecoCusto.getText().replace(",", "."));
            }
                
            Double preco_venda = Double.valueOf(txtfldPrecoVenda.getText().replace(",", "."));
            LocalDate data_preco_item = dtpckrDataCompra.getValue();
            int quantidade = spnrQuantidade.getValue();
            char tipo_item = checkSelectedRadioButton();
            String descricao_item = txtfldDescricao.getText();

            return new Item(nome_item, preco_custo, preco_venda, data_preco_item, quantidade, tipo_item, descricao_item);
        }catch (NumberFormatException e) {
        e.printStackTrace();
        return null;
        }
    }
}
