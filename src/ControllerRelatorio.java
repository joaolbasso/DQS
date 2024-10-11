import DAO.EntityManagerFactorySingleton;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ControllerRelatorio implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    public void relatorioTeste(ActionEvent event) throws IOException, JRException {
        gerarRelatorio("a", "b");
    }
    

    public void gerarRelatorio(String filtro1, String filtro2) {
        EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            Connection connection = DAO.DBConnection.getConnection();

            // Construir a query dinâmica
            String query = "SELECT * FROM cliente";

            // Use o caminho relativo para o arquivo .jrxml
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Relatorio/query.jrxml");
            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo de relatório não encontrado: Relatorio/query.jrxml");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("QUERY", query);

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
