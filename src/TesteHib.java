import DAO.BeneficiarioDAO;
import DAO.CidadeDAO;
import DAO.VendaDAO;
import Model.Beneficiario;
import Model.Caixa;
import Model.Estado;
import Model.Usuario;
import Model.Cidade;
import Model.Cliente;
import Model.Despesa;
import Model.Item;
import Model.Item_caixa;
import Model.Item_venda;
import Model.Pagamento;
import Model.Parcela;
import Model.Venda;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteHib {
    public static void main(String[] args) {
     
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DQSPU");
        EntityManager em = emf.createEntityManager();
        
        //Venda venda = new Venda(50.0, LocalDate.now(), em.find(Cliente.class, 1));
        
        //Parcela parcela = new Parcela(50.0, 'P', venda);
        //Pagamento pagamento = new Pagamento('P', 'C', LocalDate.now(), 50.0, parcela);
        
        //em.getTransaction().begin();
        
        //em.persist(venda);
        //em.persist(parcela);
        //em.persist(pagamento);
        //em.getTransaction().commit();
        
        //em.close();
        //emf.close();
        
    }
}