import DAO.BeneficiarioDAO;
import DAO.CaixaDAO;
import DAO.DespesaDAO;
import DAO.ItemDAO;
import DAO.Item_caixaDAO;
import DAO.UsuarioDAO;
import Model.Beneficiario;
import Model.Caixa;
import Model.Despesa;
import Model.Item;
import Model.Item_caixa;
import Model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ControllerRegistrarDespesa implements Initializable {

    private Despesa despesaEdicao;

    @FXML
    private Label txtTitulo;
    @FXML
    private TextField txtfldNomeDespesa;
    @FXML
    private TextField txtfldValor;
    @FXML
    private Spinner<Integer> spnrRecorrente;
    @FXML
    private ComboBox<Beneficiario> cmbboxBeneficiario;
    @FXML
    private DatePicker dtpkrDataPagamento;
    @FXML
    private RadioButton rbtnPago, rbtnASerPago;
    @FXML
    private ToggleGroup situacao;
    @FXML
    private DatePicker dtpkrDataVencimento;
    @FXML
    private TextField txtfldDescricao;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dtpkrDataPagamento.setValue(LocalDate.now());

        situacao.selectedToggleProperty().addListener((ObservableValue<? extends javafx.scene.control.Toggle> observable, javafx.scene.control.Toggle oldValue, javafx.scene.control.Toggle newValue) -> {
            RadioButton selectedRadioButton = (RadioButton) newValue;
            if (selectedRadioButton == rbtnASerPago) {
                dtpkrDataPagamento.setDisable(true);
                dtpkrDataPagamento.setValue(null);
                dtpkrDataVencimento.setValue(LocalDate.now().plusDays(10));
            } else {
                dtpkrDataPagamento.setDisable(false);
                dtpkrDataPagamento.setValue(LocalDate.now());
                dtpkrDataVencimento.setValue(null);
            } 
        });

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 31);
        valueFactory.setValue(0);
        spnrRecorrente.setValueFactory(valueFactory);

        BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
        ObservableList<Beneficiario> beneficiarios = FXCollections.observableArrayList(beneficiarioDAO.todosOsBeneficiarios());
        cmbboxBeneficiario.setItems(beneficiarios);

        cmbboxBeneficiario.setCellFactory(cell -> new ListCell<Beneficiario>() {
            protected void updateItem(Beneficiario beneficiario, boolean empty) {
                super.updateItem(beneficiario, empty);
                if (empty || beneficiario == null) {
                    setText(null);
                } else {
                    setText(beneficiario.getNome_beneficiario());
                }
            }
        });

        cmbboxBeneficiario.setButtonCell(cmbboxBeneficiario.getCellFactory().call(null));

        if (despesaEdicao != null) {
            txtTitulo.setText("Editar Despesa");
        }

        // Validação de entrada para o campo de valor
        txtfldValor.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir apenas números e uma vírgula ou ponto para decimais
            if (!newValue.matches("\\d*([\\.,]\\d{0,2})?")) {
                txtfldValor.setText(oldValue);
            } else {
                // Substituir vírgula por ponto para manter o formato decimal correto
                txtfldValor.setText(newValue.replace(",", "."));
            }
        });
    }

    @FXML
    public void voltar(ActionEvent event) throws IOException {
        String nomeDaView = "Despesa.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
        Parent view = loader.load();

        Scene cena = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cena);
        window.show();
    }

    public void limparCampos(ActionEvent event) {
        txtfldNomeDespesa.setText("");
        txtfldValor.setText("");
        txtfldDescricao.setText("");
        dtpkrDataPagamento.setValue(null);
        dtpkrDataVencimento.setValue(null);
        cmbboxBeneficiario.getSelectionModel().clearSelection();
        spnrRecorrente.getValueFactory().setValue(0);
    }

    public void setDespesa(Despesa despesa) {
    this.despesaEdicao = despesa;
    if (despesa != null) {
        txtTitulo.setText("Editar Despesa");
        txtfldNomeDespesa.setText(despesa.getNome_despesa());

        txtfldValor.setText(despesa.getValor_despesa().toString());

        spnrRecorrente.getValueFactory().setValue(despesa.getDia_pagamento_despesa());
        dtpkrDataPagamento.setValue(despesa.getData_pagamento_despesa());
        dtpkrDataVencimento.setValue(despesa.getData_vencimento_despesa());
        txtfldDescricao.setText(despesa.getDescricao_despesa());
        cmbboxBeneficiario.getSelectionModel().select(despesa.getBeneficiario());
    }
}

    @FXML
    public void registrarDespesa(ActionEvent event) {
        try {
            Despesa despesaSalvar = criarDespesa();
            if (despesaSalvar != null) {
                DespesaDAO despesaDAO = new DespesaDAO();
                if (despesaEdicao == null) {
                    // Nova despesa
                    despesaDAO.insert(despesaSalvar);
                    JOptionPane.showMessageDialog(null, "Despesa registrada com sucesso!");
                } else {
                    // Editar despesa existente
                    despesaSalvar.setId_despesa(despesaEdicao.getId_despesa());
                    despesaDAO.update(despesaSalvar);
                    JOptionPane.showMessageDialog(null, "Despesa editada com sucesso!");
                    voltar(event);
                }
                limparCampos(event);
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir a DESPESA!", "Erro ao inserir", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar a despesa.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Despesa criarDespesa() {
    try {
        CaixaDAO caixaDAO = new CaixaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Item_caixaDAO item_caixaDAO = new Item_caixaDAO();
        
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
        
        caixaDAO.update(caixaAtual);
        }
        
        String nome_despesa = txtfldNomeDespesa.getText();

        // Converter a string para Double
        Double valor_despesa = Double.valueOf(txtfldValor.getText().replace(",", "."));

        int recorrencia_despesa = spnrRecorrente.getValue();
        LocalDate data_pagamento_despesa = dtpkrDataPagamento.getValue();
        LocalDate data_vencimento_despesa = dtpkrDataVencimento.getValue();
        String descricao_despesa = txtfldDescricao.getText();
        Beneficiario beneficiario = cmbboxBeneficiario.getSelectionModel().getSelectedItem();
        
        if (nome_despesa.isEmpty() || valor_despesa.isNaN()) {
            return null;
        }
        
        if (despesaEdicao == null) {
            Item_caixa item_caixa = new Item_caixa(valor_despesa, LocalDate.now(), Item_caixa.TipoOperacao.D, caixaAtual, '-', nome_despesa);
            item_caixaDAO.insert(item_caixa);
            return new Despesa(nome_despesa, valor_despesa, descricao_despesa, recorrencia_despesa, data_vencimento_despesa, data_pagamento_despesa, beneficiario, item_caixa);
        } else {
            return new Despesa(nome_despesa, valor_despesa, descricao_despesa, recorrencia_despesa, data_vencimento_despesa, data_pagamento_despesa, beneficiario);
        }
        
    } catch (NumberFormatException e) {
        e.printStackTrace();
        return null;
    }
}
    
    public void entrarCadastrarBeneficiario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CadastrarBeneficiario.fxml"));
        Parent cadastrarBeneficiarioView = loader.load();

        ControllerCadastrarBeneficiario controllerCadastrarBeneficiario = loader.getController();
        
        controllerCadastrarBeneficiario.setItemCallBack(() -> {
        // Atualizar a ComboBox
        atualizaComboBoxBeneficiario();
        });
        
        controllerCadastrarBeneficiario.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene cadastrarBeneficiarioScene = new Scene(cadastrarBeneficiarioView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cadastrarBeneficiarioScene);
        window.show();
    }

    private void atualizaComboBoxBeneficiario() {
        BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
        ObservableList<Beneficiario> beneficiarios = FXCollections.observableArrayList(beneficiarioDAO.todosOsBeneficiarios());
        cmbboxBeneficiario.setItems(beneficiarios);
    }
}