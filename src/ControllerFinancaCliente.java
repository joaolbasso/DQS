import Model.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class ControllerFinancaCliente implements Initializable {

    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
