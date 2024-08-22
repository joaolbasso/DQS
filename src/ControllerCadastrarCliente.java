import Config.MascarasFX;
import DAO.CidadeDAO;
import DAO.ClienteDAO;
import Model.Cidade;
import Model.Cliente;
import Model.Estado;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class ControllerCadastrarCliente implements Initializable {
    
    private Scene cenaAnterior;

    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
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
    private TextField txtfldNome;
    
    @FXML
    private TextField txtfldCPF;
    
    @FXML
    private TextField txtfldTelefone;
    
    @FXML
    private ComboBox<Cidade> cmbboxCidade;
    
    @FXML
    private ComboBox<Estado> cmbboxEstado;
    
    @FXML
    private TextField txtfldCEP;
    
    @FXML
    private TextField txtfldLogradouro;
    
    @FXML
    private TextField txtfldNumero;
    
    @FXML
    private TextField txtfldComplemento;
    
    @FXML
    private TextField txtfldBairro;
    
    public void cadastrarCliente(ActionEvent event) throws IOException {
        Cliente cliente = criarCliente();
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.insert(cliente);
        limpezaCampos();
    }
    
    public void limparCampos(ActionEvent event) throws IOException {
        int resposta = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja limpar os campos?", "Limpar Campos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            limpezaCampos();
        }
    }
    
    private void limpezaCampos() {
        txtfldNome.setText("");
        txtfldCPF.setText("");
        txtfldTelefone.setText("");
        txtfldCEP.setText("");
        txtfldLogradouro.setText("");
        txtfldNumero.setText("");
        txtfldComplemento.setText("");
        txtfldBairro.setText("");
    }
    
    private Cliente criarCliente() {
        String nome_cliente = txtfldNome.getText();
        String telefone = txtfldTelefone.getText();
        String cpf = txtfldCPF.getText();
        
        System.out.println(cpf);
        
        String cep = txtfldCEP.getText();
        String logradouro = txtfldLogradouro.getText();
        String bairro = txtfldBairro.getText();
        String numero = txtfldNumero.getText();
        String complemento = txtfldComplemento.getText();
        Cidade cidade = cmbboxCidade.getSelectionModel().getSelectedItem();
        Cliente cliente = new Cliente(nome_cliente, telefone, cpf, logradouro, bairro, cep, numero, complemento, cidade);
        return cliente;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MascarasFX.mascaraCPF(txtfldCPF);
        //MascarasFX.mascaraCEP(txtfldCEP);
        //MascarasFX.mascaraTelefone(txtfldTelefone);
        
        
        CidadeDAO cidadeDAO = new CidadeDAO();
        
        ObservableList<Cidade> cidades = FXCollections.observableArrayList(cidadeDAO.todasAsCidades(1));
        cmbboxCidade.setItems(cidades);
        
        cmbboxCidade.setCellFactory(cell -> new ListCell<Cidade>() {
            @Override
            protected void updateItem(Cidade cidade, boolean empty) {
                super.updateItem(cidade, empty);
                if (empty || cidade == null) {
                    setText(null);
                } else {
                    setText(cidade.getNome_cidade());
                }
            }
        });
        
        cmbboxCidade.setButtonCell(cmbboxCidade.getCellFactory().call(null));
        cmbboxCidade.setValue(cidades.get(347));

    }    
}
