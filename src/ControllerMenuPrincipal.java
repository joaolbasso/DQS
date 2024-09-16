import DAO.CaixaDAO;
import DAO.DespesaDAO;
import DAO.Item_caixaDAO;
import DAO.UsuarioDAO;
import Model.Caixa;
import Model.Despesa;
import Model.Item_caixa;
import Model.Pagamento;
import Model.Usuario;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author VIDEO
 */
public class ControllerMenuPrincipal implements Initializable {

    @FXML
    private Button btnCaixa;
    @FXML
    private Button btnDespesas;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnItens;
    @FXML
    private Button btnCaixaAbrir;
    
    @FXML
    private Button btnSangria;
    
    private Caixa caixa;

    @FXML
    private Label lblTitulo;
    
    
    @FXML
    private Label lblEntradas;
    @FXML
    private Label lblSaidas;
    @FXML
    private Label lblSaldoCaixa;
    
    @FXML
    private TableView<Item_caixa> tbvwItensCaixa;
    
    @FXML
    private Button btnAbrirCaixa;
    
    @FXML
    private Button btnFecharCaixa;
    
    @FXML
    private TableColumn<Item_caixa, String> tbclnDescricao = new TableColumn<>("Item/Itens");
    @FXML
    private TableColumn<Item_caixa, Double> tbclnValor = new TableColumn<>("Valor do Item");
    @FXML
    private TableColumn<Item_caixa, LocalDateTime> tbclnData = new TableColumn<>("Data do Item");
    @FXML
    private TableColumn<Item_caixa, String> tbclnOperacao = new TableColumn<>("Operação");
    
    @FXML
    private TableView<Despesa> tbvwDespesas;
    @FXML
    private TableColumn<Despesa, String> tbclnBeneficiario;
    @FXML
    private TableColumn<Despesa, LocalDate> tbclnNome;
    @FXML
    private TableColumn<Despesa, Double> tbclnValorDespesa;
    @FXML
    private TableColumn<Despesa, LocalDate> tbclnVencimento;
    
    Item_caixaDAO item_caixaDAO = new Item_caixaDAO();
    private ObservableList<Item_caixa> itens_caixa = FXCollections.observableArrayList();
    private DespesaDAO despesaDAO = new DespesaDAO();
    private ObservableList<Despesa> despesas = FXCollections.observableArrayList();
    
    private CaixaDAO caixaDAO = new CaixaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @FXML
    public void abrirCaixa(ActionEvent event) throws IOException {
        
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
                "Deseja fazer um aporte inicial no caixa? Insira o valor: ",
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
        
        Caixa caixaNovo = new Caixa(usuario);
        caixaDAO.insert(caixaNovo);
        Item_caixa item_aporte = new Item_caixa(valor, LocalDate.now(), Item_caixa.TipoOperacao.A, caixaNovo, 'A', "Aporte Inicial");
        
        System.out.println(item_aporte.getValor_item_caixa()); 
        System.out.println(item_aporte.getTipo_operacao());
        System.out.println(item_aporte.getCaixa().getData_hora_abertura());
        System.out.println(item_aporte.getData_hora());
        System.out.println(item_aporte.getTipo_operacao());
        
        caixaNovo.getItens_caixa().add(item_aporte);
        
        item_caixaDAO.insert(item_aporte);
        
        caixaDAO.update(caixaNovo);
        
        JOptionPane.showMessageDialog(null, "Caixa aberto com sucesso!", "Caixa aberto com sucesso!", JOptionPane.INFORMATION_MESSAGE);
        updateUI();
    }
    
    @FXML
    public void fecharCaixa(ActionEvent event) throws IOException {
        Caixa caixaFechar = getCaixa();
        
        caixaFechar.setData_hora_fechamento(LocalDateTime.now());
        caixaFechar.setEstado_caixa(Caixa.StatusCaixa.FECHADO);
        
        caixaDAO.fecharCaixa(caixaFechar);
        JOptionPane.showMessageDialog(null, "Caixa fechado com sucesso!", "Caixa fechado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
        updateUI();
        
        
    }
    
    @FXML
    public void entrarCaixa(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Caixa.fxml"));
        Parent caixaView = loader.load();
        
        // Obter o controlador da nova tela
        ControllerCaixa controller = loader.getController();
        
        // Passar a cena atual (Menu Principal) para o controlador da nova tela
        controller.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene caixaScene = new Scene(caixaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(caixaScene);
        window.show();
    }
    
    @FXML
    public void entrarDespesas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Despesa.fxml"));
        Parent despesaView = loader.load();
        
        // Obter o controlador da nova tela
        ControllerDespesa controller = loader.getController();
        
        // Passar a cena atual (Menu Principal) para o controlador da nova tela
//        controller.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene despesaScene = new Scene(despesaView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(despesaScene);
        window.show();
    }
    
    @FXML
    public void entrarClientes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Cliente.fxml"));
        Parent clienteView = loader.load();

        Scene clienteScene = new Scene(clienteView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(clienteScene);
        window.show();
    }
    
    @FXML
    public void entrarItens(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Item.fxml"));
        Parent itemView = loader.load();
        
        // Obter o controlador da nova tela
        ControllerItem controller = loader.getController();
        
        // Passar a cena atual (Menu Principal) para o controlador da nova tela
        //controller.setCenaAnterior(((Node) event.getSource()).getScene());

        Scene itemScene = new Scene(itemView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(itemScene);
        window.show();
    }
    
    @FXML
    public void sangriaCaixa(ActionEvent event) throws IOException {
        //So entra quando caixa aberto
        String input;
        double valor = 0;
        boolean entradaValida = false;
        ImageIcon icon = new ImageIcon("View/Icons/aporte.png");
        
        if (saldoCaixa <= 0.0) {
            JOptionPane.showMessageDialog(null, "Não há saldo em caixa para sangria", "Não há saldo!", 0);
            return;
        }
        
        // Loop até obter uma entrada válida
        while (!entradaValida) {
            // Solicita a entrada do usuário
            input = (String) JOptionPane.showInputDialog(null,
                "Deseja fazer uma sangria no caixa? Insira o valor:",
                "Sangria",
                JOptionPane.YES_NO_OPTION,
                icon,
                null,
                null
            );

            // Verifica se a entrada é nula (usuário clicou em Cancelar)
            if (input == null) {
                return;
            }

            try {
                // Tenta converter a entrada para um número de ponto flutuante
                valor = Double.parseDouble(input);

                // Verifica se o valor está dentro dos limites
                if (valor > 0.0 && valor <= saldoCaixa) {
                    entradaValida = true; // Entrada válida, saia do loop
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Por favor, digite um número entre R$0.01 e " + "R$ " + saldoCaixa,
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
        
        
        Caixa caixa_atual = caixaDAO.buscarCaixaAberto();
        Item_caixa item_sangria = new Item_caixa(valor, LocalDate.now(), Item_caixa.TipoOperacao.S, caixa_atual, 'S', "Sangria (Retirou dinheiro)");
        caixa_atual.getItens_caixa().add(item_sangria);
        
        caixaDAO.update(caixa_atual);
        
        JOptionPane.showMessageDialog(null, "Sangria realizada com sucesso!", "Confirmação de sangria", 1);
        
        updateUI();
    }

    private Double saldoCaixa;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.caixa = caixaDAO.buscarCaixaAberto();
        
        configurarTabela();
        carregarDespesas();
        
        if (this.caixa == null) {
            btnAbrirCaixa.setDisable(false);
            btnFecharCaixa.setDisable(true);
            btnSangria.setDisable(true);
            lblTitulo.setText("Não há um caixa aberto!");
            lblSaldoCaixa.setText("R$ -");
        } else {
            btnAbrirCaixa.setDisable(true);
            btnFecharCaixa.setDisable(false);
            btnSangria.setDisable(false);
            lblTitulo.setText("Há um caixa aberto!");
            
            itens_caixa.addAll(item_caixaDAO.todosOsItens_Caixa(this.caixa));
            tbvwItensCaixa.setItems(itens_caixa);
            tbclnData.setCellValueFactory(new PropertyValueFactory<>("data_hora"));
            tbclnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao_item_caixa"));
            tbclnValor.setCellValueFactory(new PropertyValueFactory<>("valor_item_caixa"));
            tbclnOperacao.setCellValueFactory(new PropertyValueFactory<>("tipo_operacao"));
        
            centralizarTextoNaColuna(tbclnData);
            centralizarTextoNaColuna(tbclnDescricao);
            centralizarTextoNaColuna(tbclnValor);
            centralizarTextoNaColuna(tbclnOperacao);
            
            Double entradaCaixa = caixaDAO.entradasCaixa(this.caixa);
            Double saidasCaixa = caixaDAO.saidasCaixa(this.caixa);
            
            if (entradaCaixa != null) { //Fazer validação de entrada e saida separada
                lblEntradas.setText("R$ " + String.format("%.2f", entradaCaixa));
            } else {
                entradaCaixa = 0.0;
                lblEntradas.setText("R$ 0.00");
                
            }
            
            if (saidasCaixa != null) {
                lblSaidas.setText("R$ " + String.format("%.2f", saidasCaixa));
            } else {
                saidasCaixa = 0.0;
                lblSaidas.setText("R$ 0.00");
            }
            
            saldoCaixa = entradaCaixa - saidasCaixa;
            lblSaldoCaixa.setText("R$ " + String.format("%.2f", saldoCaixa));
        // TODO
        btnCaixa.getStyleClass().add("color-button");
        btnDespesas.getStyleClass().add("color-button");
        btnClientes.getStyleClass().add("color-button");
        btnItens.getStyleClass().add("color-button");
    }
    }
    
    private void configurarTabela() {
        tbclnBeneficiario.setCellValueFactory(new PropertyValueFactory<>("beneficiario"));
        tbclnNome.setCellValueFactory(new PropertyValueFactory<>("nome_despesa"));
        tbclnValorDespesa.setCellValueFactory(new PropertyValueFactory<>("valor_despesa"));
        tbclnVencimento.setCellValueFactory(new PropertyValueFactory<>("data_vencimento_despesa"));

        centralizarTextoNaColuna(tbclnBeneficiario);
        centralizarTextoNaColuna(tbclnNome);
        centralizarTextoNaColuna(tbclnValorDespesa);
        centralizarTextoNaColuna(tbclnVencimento);
    }

    private void carregarDespesas() {
        despesas.setAll(despesaDAO.buscarDespesasNaoPagas());
        tbvwDespesas.setItems(despesas);
    }
        
    private <T, U> void centralizarTextoNaColuna(TableColumn<T, U> coluna) {
        coluna.setCellFactory(column -> new TableCell<T, U>() {
            @Override
            protected void updateItem(U item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    setStyle("-fx-alignment: CENTER;"); // Centraliza o texto
                }
            }
        });
    }
    
    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    private void updateUI() {
    try {
        // Obter o Stage atual
        Stage stage = (Stage) btnFecharCaixa.getScene().getWindow();
        
        // Carregar o novo arquivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MenuPrincipal.fxml"));
        Parent root = loader.load();
        
        // Definir a nova cena
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        // Opcional: Atualizar o título da janela ou outros componentes
        stage.setTitle("DQS - Sistema de informação");
        
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}

