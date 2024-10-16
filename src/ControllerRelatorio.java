import DAO.EntityManagerFactorySingleton;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

    ObservableList<String> filtros = FXCollections.observableArrayList("Vendas", "Clientes", "Itens", "Despesas");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbboxFiltroRelatorio.setItems(filtros);
        cmbboxFiltroRelatorio.getSelectionModel().select(0);
        
        dtpkrDataInicial.setValue(LocalDate.now().minusDays(7));
        dtpkrDataFinal.setValue(LocalDate.now());
    }
    
    @FXML
    public void geraRelatorio(ActionEvent event) throws IOException, JRException {
        LocalDate dataInicio = dtpkrDataInicial.getValue();
        LocalDate dataFim = dtpkrDataFinal.getValue();
        gerarRelatorio(dataInicio, dataFim);
    }
    

    public void gerarRelatorio(LocalDate dataInicio, LocalDate dataFim) {
        String caminho = cmbboxFiltroRelatorio.getSelectionModel().getSelectedItem().toString();
        
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = null;
        
        System.out.println(caminho);
        try {
            em = emf.createEntityManager();
            Connection connection = DAO.DBConnection.getConnection();
            
            // Use o caminho relativo para o arquivo .jrxml
            InputStream caminhoRelatorio = getClass().getClassLoader().getResourceAsStream("Relatorio/" + caminho + ".jrxml");
            if (caminhoRelatorio == null) {
                throw new IllegalArgumentException("Arquivo de relatório não encontrado");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(caminhoRelatorio);
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("startDate", Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant()));  // Conversão correta
            parameters.put("endDate", Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant()));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
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

}
