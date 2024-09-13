import DAO.CaixaDAO;
import DAO.ClienteDAO;
import DAO.Item_caixaDAO;
import DAO.PagamentoDAO;
import DAO.ParcelaDAO;
import DAO.UsuarioDAO;
import Model.Caixa;
import Model.Cliente;
import Model.Item_caixa;
import Model.Pagamento;
import Model.Parcela;
import Model.Usuario;
import Model.Venda;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerPagamentoParcelado implements Initializable {

    private Scene cenaAnterior;
    private ItemCallback itemCallBack;
    private Parcela parcela;
    
    @FXML
    private Label lblValorParcela;
    
    @FXML
    private Label lblNumeroParcela;
    
    @FXML
    private ComboBox<String> cmbboxMetodoPagamento;
    
    @FXML
    private TextField txtfldValorPagamento;
    
    ObservableList<String> metodos_pagamento = FXCollections.observableArrayList("Espécie", "PIX", "Débito", "Crédito");
    
    // Método para definir a cena anterior
    public void setCenaAnterior(Scene cenaAnterior) {
        this.cenaAnterior = cenaAnterior;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
        atualizaLabels();
    }
    
    @FXML
    public void voltar(ActionEvent event) throws IOException {
    if (itemCallBack != null) {
            itemCallBack.onItemUpdated();
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(this.cenaAnterior);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbboxMetodoPagamento.setItems(metodos_pagamento);
        cmbboxMetodoPagamento.getSelectionModel().select(0);
    }    
    
    private void atualizaLabels() {
        lblNumeroParcela.setText(Integer.toString(this.parcela.getNumero_parcela()));
        lblValorParcela.setText("R$ " + this.parcela.getValor_parcela().toString() + "0");
        txtfldValorPagamento.setText(this.parcela.getValor_parcela().toString());
    }
    
    @FXML
    public void quitarParcela(ActionEvent event) throws IOException {
        PagamentoDAO pagamentoDAO = new PagamentoDAO();
        ParcelaDAO parcelaDAO = new ParcelaDAO();
        CaixaDAO caixaDAO = new CaixaDAO();
        Item_caixaDAO item_caixaDAO = new Item_caixaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        //Criar um pagamento para a parcela e atribuir no banco de dads essa atualização (merge), ver como está a lógica de PAGO e A PAGAR na tela anterior Mudar Status de Pendente para Pago
        char metodo_pagamento = cmbboxMetodoPagamento.getSelectionModel().getSelectedItem().charAt(0);
        
        if (txtfldValorPagamento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Insira um valor!");
            return;
        }
        
        Double valor_parcela = Double.valueOf(txtfldValorPagamento.getText());
        
        Pagamento pagamento = new Pagamento('c', metodo_pagamento, LocalDate.now(), valor_parcela, this.parcela);
        this.parcela.setCondicao("PAGO");
        parcelaDAO.update(this.parcela);
        pagamentoDAO.insert(pagamento);
        
        //Criar um item_caixa que irá entrar na soma atual do caixa já aberto, vai ter que fazer lógica de abrir um novo caixa por aqui também...
        Caixa caixaAtual = caixaDAO.buscarCaixaAberto();
        
        if (caixaAtual == null) {
            JOptionPane.showMessageDialog(null, "Não há um caixa existente aberto, um novo será criado", "Caixa novo criado", JOptionPane.INFORMATION_MESSAGE);
            Usuario usuario = usuarioDAO.selectUnico();
        
        final double LIMITE_MINIMO = 0.0;
        final double LIMITE_MAXIMO = 1000.0;
        
        ImageIcon icon = new ImageIcon("View/Icons/aporte.png");
        
        //
        String input;
        double valor = 0;
        boolean entradaValida = false;

        // Loop até obter uma entrada válida
        while (!entradaValida) {
            // Solicita a entrada do usuário
            input = (String) JOptionPane.showInputDialog(null,
                "Deseja fazer um aporte inicial no caixa?",
                "Aporte Inicial",
                JOptionPane.YES_NO_OPTION,
                icon,
                null,
                null
            );

            // Verifica se a entrada é nula (usuário clicou em Cancelar)
            if (input == null) {
                int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que não realizará um aporte inicial ao caixa? Isso não poderá ser feito posteriormente.", "Confirmação", 0, 0);
                if (resposta == 0) {
                    valor = 0.0;
                    break;
                } else {
                    continue;
                }
            }

            try {
                // Tenta converter a entrada para um número de ponto flutuante
                valor = Double.parseDouble(input);

                // Verifica se o valor está dentro dos limites
                if (valor >= LIMITE_MINIMO && valor <= LIMITE_MAXIMO) {
                    entradaValida = true; // Entrada válida, saia do loop
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Por favor, digite um número entre R$0.00 a R$1000.00.",
                        "Entrada Inválida",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException e) {
                // Se ocorrer uma exceção, a entrada não é um número válido
                JOptionPane.showMessageDialog(null,
                    "Por favor, digite um número ou cancele a operação.",
                    "Entrada Inválida",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        // Exibe o valor válido digitado
        //JOptionPane.showMessageDialog(null, "Você digitou: " + valor);
        
        caixaAtual = new Caixa(usuario);
        caixaDAO.insert(caixaAtual);
        Item_caixa item_aporte = new Item_caixa(valor, LocalDate.now(), Item_caixa.TipoOperacao.A, caixaAtual, 'A', "Aporte Inicial");

        caixaAtual.getItens_caixa().add(item_aporte);
        
        item_caixaDAO.insert(item_aporte);
        
        //caixaDAO.update(caixaAtual);
        
        }
        
        Item_caixa item_caixa = new Item_caixa(valor_parcela, LocalDate.now(), "Parcela " + this.parcela.getNumero_parcela() + " de " + this.parcela.getVenda().getCliente().getNome_cliente(), Item_caixa.TipoOperacao.V, metodo_pagamento, caixaAtual, pagamento);
        item_caixaDAO.insert(item_caixa);
        caixaDAO.update(caixaAtual);
    }
    
    @FXML
    public void limparCampos(ActionEvent event) throws IOException {
        System.out.println("LIMPAR CAMPOS");
        txtfldValorPagamento.setText(this.parcela.getValor_parcela().toString());
        cmbboxMetodoPagamento.getSelectionModel().select(0);
    }
    public interface ItemCallback {
        void onItemUpdated();
    }

    public void setItemCallBack(ItemCallback itemCallBack) {
        this.itemCallBack = itemCallBack;
    }
    
    
}
