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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ControllerCadastrarItem implements Initializable {

    private Item itemEditavel;


    // Método para definir o item que será editado
    public void setItem(Item item) {
        this.itemEditavel = item;
        preencherCampos(itemEditavel); // Preencher os campos com os dados do item selecionado
    }
    

    @FXML
        public void voltar(ActionEvent event) throws IOException {
            // Substitua "NomeDaView" pelo nome da view que você deseja carregar
            String nomeDaView = "MenuPrincipal.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
            Parent view = loader.load();

            Scene cena = new Scene(view);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(cena);
            window.show();
        }

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

    // Método para cadastrar ou atualizar o item
    public void cadastrarItem(ActionEvent event) throws IOException {
        ItemDAO itemDAO = new ItemDAO();
        Item itemSalvar = criarItem();

        if (itemEditavel != null) {
            itemSalvar.setId_item(itemEditavel.getId_item()); // Manter o ID do item ao editar
            itemDAO.update(itemSalvar); // Atualizar item existente
        } else {
            itemDAO.insert(itemSalvar); // Inserir novo item
        }

        voltar(event); // Voltar para a tela anterior após salvar
    }

    // Método para limpar os campos
    public void limparCampos(ActionEvent event) throws IOException {
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

    // Preencher os campos com os dados do item ao editar
    private void preencherCampos(Item item) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dtpckrDataCompra.setValue(LocalDate.now());
        criaSpinnerValueFactory();
    }

    // Método para configurar o Spinner para a quantidade
    public void criaSpinnerValueFactory() {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999);
        valueFactory.setValue(1);
        spnrQuantidade.setValueFactory(valueFactory);
    }

    // Método para determinar o tipo do item com base no RadioButton selecionado
    private char checkSelectedRadioButton() {
        RadioButton selectedRadioButton = (RadioButton) tipoItem.getSelectedToggle();
        return selectedRadioButton.getText().charAt(0);
    }

    // Método para criar um item a partir dos dados dos campos
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
