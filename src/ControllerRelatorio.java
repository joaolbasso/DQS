import DAO.ClienteDAO;
import DAO.EntityManagerFactorySingleton;
import Model.Cliente;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ControllerRelatorio implements Initializable {
    
    @FXML
    private ComboBox cmbboxFiltroRelatorio;
    
    @FXML
    private DatePicker dtpkrDataInicial;
    
    @FXML
    private DatePicker dtpkrDataFinal;
    
    @FXML
    private TextField txtfldBuscarCliente;
    
    @FXML
    private ListView<Cliente> listViewClientes;
            
    private ObservableList<String> filtros = FXCollections.observableArrayList("Vendas", "Cliente Devedor (individual)", "Clientes Devedores(grupo)", "Entradas e Saídas");
    private ObservableList<Cliente> clienteObservableList = FXCollections.observableArrayList();
    private Cliente clienteSelecionado;
    private ClienteDAO clienteDAO = new ClienteDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listViewClientes.setVisible(false);
        listViewClientes.setCellFactory(cell -> new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente cliente, boolean empty) {
                super.updateItem(cliente, empty);
                if (cliente != null) {
                    setText(cliente.getNome_cliente()); // Exibir o nome do cliente
                } else {
                    setText(null);
                }
            }
        });
        
        txtfldBuscarCliente.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (null != event.getCode()) switch (event.getCode()) {
                    case DOWN:
                        // Se a lista estiver visível, move a seleção para baixo
                        if (listViewClientes.isVisible()) {
                            int index = listViewClientes.getSelectionModel().getSelectedIndex();
                            if (index < clienteObservableList.size() - 1) {
                                listViewClientes.getSelectionModel().selectNext();
                                listViewClientes.scrollTo(index + 1); // Rolagem para o item selecionado
                            }
                            event.consume(); // Impede que o evento se propague
                        }   break;
                    case ENTER:
                        // Se a lista estiver visível, seleciona o item
                        if (listViewClientes.isVisible() && !listViewClientes.getItems().isEmpty()) {
                            clienteSelecionado = listViewClientes.getSelectionModel().getSelectedItem();
                            if (clienteSelecionado != null) {
                                txtfldBuscarCliente.setText(clienteSelecionado.getNome_cliente());
                                listViewClientes.setVisible(false); // Esconder a lista após a seleção
                            }
                            event.consume(); // Impede que o evento se propague
                        }   break;
                    case UP:
                        // Se a lista estiver visível, move a seleção para cima
                        if (listViewClientes.isVisible()) {
                            int index = listViewClientes.getSelectionModel().getSelectedIndex();
                            if (index > 0) {
                                listViewClientes.getSelectionModel().selectPrevious();
                                listViewClientes.scrollTo(index - 1); // Rolagem para o item selecionado
                            }
                            event.consume(); // Impede que o evento se propague
                        }   break;
                    default:
                        break;
                }
            }
        });
        
        txtfldBuscarCliente.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                // Carrega os clientes que correspondem ao texto digitado
                List<Cliente> filteredClients = clienteDAO.buscarClientesLazy(newValue.trim());
                clienteObservableList.setAll(filteredClients);
                listViewClientes.setItems(clienteObservableList);
                listViewClientes.setVisible(true);
                } else {
                    listViewClientes.setVisible(false);
                }
        });
        
        listViewClientes.setOnMouseClicked(event -> {
            clienteSelecionado = listViewClientes.getSelectionModel().getSelectedItem();
            if (clienteSelecionado != null) {
                txtfldBuscarCliente.setText(clienteSelecionado.getNome_cliente());
                listViewClientes.setVisible(false);
            }
        });
        
        cmbboxFiltroRelatorio.setItems(filtros);
        cmbboxFiltroRelatorio.getSelectionModel().select(0);
        
        dtpkrDataInicial.setValue(LocalDate.now().minusDays(7));
        dtpkrDataFinal.setValue(LocalDate.now());
    }
    
    @FXML
    public void geraRelatorio(ActionEvent event) throws IOException, JRException {
        int index = cmbboxFiltroRelatorio.getSelectionModel().getSelectedIndex();
        switch(index) {
            case 0:
                LocalDate dataInicio = dtpkrDataInicial.getValue();
                LocalDate dataFim = dtpkrDataFinal.getValue();
                gerarRelatorioVendas(dataInicio, dataFim);
                break;
                
            case 1:
                gerarRelatorioClienteIndividual(clienteSelecionado.getNome_cliente());
                break;
                
            case 2:
                System.out.println("Gerar Cliente grp");
                break;
                
            case 3:
                System.out.println("Gerar Entradas e saidas");
                break;
        }
        
    }
    
    @FXML
    public void voltar(ActionEvent event) throws IOException {
        String nomeDaView = "MenuPrincipal.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/" + nomeDaView));
        Parent view = loader.load();

        Scene cena = new Scene(view);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(cena);
        window.show();
    }
    
    private void gerarRelatorioVendas(LocalDate dataInicio, LocalDate dataFim) throws JRException {
        System.out.println("ENTROU VENDAS");
        
        if(dataInicio.isAfter(dataFim)) {
            JOptionPane.showMessageDialog(null, "Data inicial é maior que data final... Selecione uma data válida");
            return;
        }
        
        if(dataInicio.isAfter(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Data inicial é maior que data do dia de hoje... Selecione uma data válida");
            return;
        }
        
         EntityManager em = null;
        try {
            EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
            em = emf.createEntityManager();
            // Use o caminho relativo para o arquivo .jrxml
            try (Connection connection = DAO.DBConnection.getConnection()) {
                // Use o caminho relativo para o arquivo .jrxml
                InputStream caminhoRelatorio = getClass().getClassLoader().getResourceAsStream("Relatorio/Vendas.jrxml");
                if (caminhoRelatorio == null) {
                    throw new IllegalArgumentException("Arquivo de relatório não encontrado");
                }
                JasperReport jasperReport = JasperCompileManager.compileReport(caminhoRelatorio);
                
                Map<String, Object> parameters = new HashMap<>();
                
                //Caso Vendas
                parameters.put("startDate", Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant()));  // Conversão correta
                parameters.put("endDate", Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
                
                JasperViewer.viewReport(jasperPrint, false);
            }

        } catch (IllegalArgumentException | SQLException | JRException e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
                
            }
        }
    }

    private void gerarRelatorioClienteIndividual(String nome_cliente) throws JRException {
        System.out.println("ENTROU CLINTE IND");
         EntityManager em = null;
        try {
            EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
            em = emf.createEntityManager();
            // Use o caminho relativo para o arquivo .jrxml
            try (Connection connection = DAO.DBConnection.getConnection()) {
                // Use o caminho relativo para o arquivo .jrxml
                InputStream caminhoRelatorio = getClass().getClassLoader().getResourceAsStream("Relatorio/Cliente Devedor (individual).jrxml");
                if (caminhoRelatorio == null) {
                    throw new IllegalArgumentException("Arquivo de relatório não encontrado");
                }
                JasperReport jasperReport = JasperCompileManager.compileReport(caminhoRelatorio);
                
                Map<String, Object> parameters = new HashMap<>();
                
                //Caso Vendas
                parameters.put("nomeClienteRelatorio", nome_cliente);  // Conversão correta
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
                
                JasperViewer.viewReport(jasperPrint, false);
            }

        } catch (IllegalArgumentException | SQLException | JRException e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
                
            }
        }
    }

}
