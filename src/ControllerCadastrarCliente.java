import Config.MascarasFX;
import DAO.CidadeDAO;
import DAO.ClienteDAO;
import DAO.EstadoDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ControllerCadastrarCliente implements Initializable {

    private Scene cenaAnterior;
    private Cliente clienteEdicao;
    private ItemCallback itemCallBack;
    
    @FXML
    private Label txtTitulo;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //aplicarMascaraCPF(txtfldCPF);
        //aplicarMascaraTelefone(txtfldTelefone);
        //aplicarMascaraCEP(txtfldCEP);

        CidadeDAO cidadeDAO = new CidadeDAO();
        ObservableList<Cidade> cidades = FXCollections.observableArrayList(cidadeDAO.todasAsCidades(1));
        cmbboxCidade.setItems(cidades);
        
        EstadoDAO estadoDAO = new EstadoDAO();
        ObservableList<Estado> estados = FXCollections.observableArrayList(estadoDAO.todosOsEstados());
        cmbboxEstado.setItems(estados);

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
        
        cmbboxEstado.setCellFactory(cell -> new ListCell<Estado>() {
            @Override
            protected void updateItem(Estado estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) {
                    setText(null);
                } else {
                    setText(estado.getSigla_uf());
                }
            }
        });

        cmbboxCidade.setButtonCell(cmbboxCidade.getCellFactory().call(null));
        cmbboxEstado.setButtonCell(cmbboxEstado.getCellFactory().call(null));
        
        cmbboxCidade.getSelectionModel().select(347);
        cmbboxEstado.getSelectionModel().select(0);
        txtfldCEP.setText("84530-000");
        
        if (clienteEdicao != null) {
            txtTitulo.setText("Editar Cliente");
        }
        
        // Validação de entrada
        txtfldCPF.textProperty().addListener((observable, oldValue, newValue) -> {
    if (!newValue.matches("\\d*")) {
        txtfldCPF.setText(oldValue);
    } else if (newValue.length() > 11) {
        txtfldCPF.setText(oldValue);
    }
});

txtfldTelefone.textProperty().addListener((observable, oldValue, newValue) -> {
    if (!newValue.matches("\\d*")) {
        txtfldTelefone.setText(oldValue);
    } else if (newValue.length() > 11) {
        txtfldTelefone.setText(oldValue);
    } else if (newValue.length() == 11) {
        txtfldTelefone.setText(newValue);
    }
});

        txtfldCEP.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtfldCEP.setText(oldValue);
            }
        });
        txtfldNumero.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtfldNumero.setText(oldValue);
            }
        });
        
    }

    public interface ItemCallback {
        void onItemUpdated();
    } 

    public void setItemCallBack(ItemCallback itemCallBack) {
        this.itemCallBack = itemCallBack;
    }
    
    @FXML
    public void cadastrarCliente(ActionEvent event) {
         ClienteDAO clienteDAO = new ClienteDAO();
        if (!Config.ValidaCPF.isCPF(txtfldCPF.getText() )) {
            JOptionPane.showMessageDialog(null, "CPF não é válido!", "CPF Inválido", 0);
            return;
        }
        
        if(txtfldTelefone.getText().length() != 11) {
            JOptionPane.showMessageDialog(null, "Telefone não é válido, não tem 11 números!", "Telefone Inválido", 0);
            return;
        }
        
        if (clienteDAO.cpfExiste(txtfldCPF.getText()) && clienteEdicao == null) {
            JOptionPane.showMessageDialog(null, "CPF já inserido no sistema!", "CPF Repetido", 0);
        }
        else { 
        try {
            Cliente clienteSalvar = criarCliente();
            if (clienteSalvar != null) {
               
                if (clienteEdicao == null) {
                    // Nova cliente
                    clienteDAO.insert(clienteSalvar);
                    JOptionPane.showMessageDialog(null, "Cliente registrada com sucesso!");
                } else {
                    // Editar cliente existente
                    clienteSalvar.setId_cliente(clienteEdicao.getId_cliente());
                    clienteDAO.update(clienteSalvar);
                    JOptionPane.showMessageDialog(null, "Cliente editada com sucesso!");
                    voltar(event);
                }
                limparCampos(event);
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir a Cliente!", "Erro ao inserir", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar a Cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        }
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + getCenaAnterior()));
        //Parent view = loader.load();

        //Scene cena = new Scene(view);
        if (itemCallBack != null) {
            itemCallBack.onItemUpdated();
        }
        
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(this.cenaAnterior);
        window.show();
    }

    public void setCliente(Cliente cliente) {
        this.clienteEdicao = cliente;
        if (cliente != null) {
            txtTitulo.setText("Editar Cliente");
            
            txtfldNome.setText(cliente.getNome_cliente());
            txtfldCPF.setText(cliente.getCpf());
            txtfldTelefone.setText(cliente.getTelefone());
            txtfldLogradouro.setText(cliente.getLogradouro());
            txtfldBairro.setText(cliente.getBairro());
            txtfldNumero.setText(cliente.getNumero());
            txtfldComplemento.setText(cliente.getComplemento());
            cmbboxCidade.getSelectionModel().select(347);
            cmbboxEstado.getSelectionModel().select(0);
            txtfldCEP.setText("84530-000");
        }
    }

    public void limparCampos(ActionEvent event) {
        txtfldNome.setText("");
        txtfldCPF.setText("");
        txtfldTelefone.setText("");
        txtfldCEP.setText("");
        txtfldLogradouro.setText("");
        txtfldNumero.setText("");
        txtfldComplemento.setText("");
        txtfldBairro.setText("");
        cmbboxCidade.getSelectionModel().clearSelection();
        cmbboxEstado.getSelectionModel().clearSelection();
    }

    private Cliente criarCliente() {
        try {
            String nome_cliente = txtfldNome.getText();
            String telefone = txtfldTelefone.getText();
            String cpf = txtfldCPF.getText();
            String cep = txtfldCEP.getText();
            String logradouro = txtfldLogradouro.getText();
            String bairro = txtfldBairro.getText();
            String numero = txtfldNumero.getText();
            String complemento = txtfldComplemento.getText();
            Cidade cidade = cmbboxCidade.getSelectionModel().getSelectedItem();
            Estado estado = cmbboxEstado.getSelectionModel().getSelectedItem();

            if (nome_cliente.isEmpty() || cpf.isEmpty() || cidade == null) {
                return null;
            }

            return new Cliente(nome_cliente, telefone, cpf, logradouro, bairro, cep, numero, complemento, cidade, estado);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }  
    }
/*
    private void aplicarMascaraCPF(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue.replaceAll("[^\\d]", "");
            if (value.length() > 11) value = value.substring(0, 11);
            value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
            value = value.replaceFirst("(\\d{3})\\.(\\d{3})(\\d)", "$1.$2.$3");
            value = value.replaceFirst("(\\d{3})\\.(\\d{3})\\.(\\d{3})(\\d)", "$1.$2.$3-$4");
            textField.setText(value);
            textField.positionCaret(value.length());
        });
    }

    private void aplicarMascaraTelefone(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue.replaceAll("[^\\d]", "");
            if (value.length() > 11) value = value.substring(0, 11);
            value = value.replaceFirst("(\\d{2})(\\d)", "($1) $2");
            value = value.replaceFirst("\\((\\d{2})\\) (\\d{5})(\\d)", "($1) $2-$3");
            textField.setText(value);
            textField.positionCaret(value.length());
        });
    }

    private void aplicarMascaraCEP(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String value = newValue.replaceAll("[^\\d]", "");
            if (value.length() > 8) value = value.substring(0, 8);
            value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
            textField.setText(value);
            textField.positionCaret(value.length());
        });
    }
*/
    public Scene getCenaAnterior() {
        return cenaAnterior;
    }

    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }
    
}