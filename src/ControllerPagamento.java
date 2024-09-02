import DAO.PagamentoDAO;
import DAO.ParcelaDAO;
import DAO.VendaDAO;
import Model.Pagamento;
import Model.Parcela;
import Model.Venda;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ControllerPagamento implements Initializable {

    private Scene cenaAnterior;
    private Venda venda;
    
    @FXML
    private Label lblValorVenda;
    
    @FXML
    private Button btnConcluir;
    
    @FXML
    private ComboBox<String> cmbboxMetodoPagamento;
    
    @FXML
    private TextField txtfldValorRecebido;
    
    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
        
    }

    public Scene getCenaAnterior() {
        return cenaAnterior;
    }

    public Venda getVenda() {
        return venda;
    }
    
    public void setVenda(Venda venda) {
        this.venda = venda;
        atualizarValorVenda();
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
    public void insereValorMaximo(ActionEvent event) throws IOException {
        txtfldValorRecebido.setText(this.venda.getValor_venda().toString());
    }
    
    @FXML
    public void concluirVenda(ActionEvent event) throws IOException, InterruptedException {
        
        if (Double.valueOf(txtfldValorRecebido.getText()) > this.venda.getValor_venda()) {
            JOptionPane.showMessageDialog(null, "Valor recebido não pode ser maior que valor da venda!!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (cmbboxMetodoPagamento.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Selecione um método de pagamento!!", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(Objects.equals(Double.valueOf(txtfldValorRecebido.getText()), this.venda.getValor_venda())) {
            char metodo_pagamento = cmbboxMetodoPagamento.getSelectionModel().getSelectedItem().charAt(0);
            Parcela parcelaUnica = new Parcela(this.venda.getValor_venda(), metodo_pagamento, this.venda);
            Pagamento pagamento = new Pagamento(metodo_pagamento, 'C', this.venda.getData_venda(), this.venda.getValor_venda(), parcelaUnica);

            ParcelaDAO parcelaDAO = new ParcelaDAO();
            parcelaDAO.insert(parcelaUnica);
            
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            pagamentoDAO.insert(pagamento);
            
            
            JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!", "Venda com sucesso!", JOptionPane.INFORMATION_MESSAGE);
            voltar(event);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PagamentoParcelado.fxml"));
            Parent pagamentoView = loader.load();
        
            // Obter o controlador da nova tela
            ControllerPagamentoParcelado controller = loader.getController();
        
            // Passar a cena atual para o controlador da nova tela
            controller.setCenaAnterior(((Node)event.getSource()).getScene());

            Scene pagamentoScene = new Scene(pagamentoView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(pagamentoScene);
            window.show();
        }
    }

    ObservableList<String> metodos_pagamento = FXCollections.observableArrayList("Espécie", "PIX", "Débito", "Crédito");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbboxMetodoPagamento.setItems(metodos_pagamento);
    }    

    private void atualizarValorVenda() {
        if (venda != null) {
            lblValorVenda.setText(String.format("R$ %.2f", venda.getValor_venda()));
        }
    }

    
}

