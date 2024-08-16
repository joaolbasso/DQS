import Model.Caixa;
import Model.Estado;
import Model.Usuario;
import Model.Cidade;
import Model.Item;
import Model.Item_caixa;
import Model.Item_venda;
import Model.Pagamento;
import Model.Parcela;
import Model.Venda;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteHib {
    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("DQSPU");
        EntityManager gerente = fabrica.createEntityManager();
        
        Item_venda iv3 = new Item_venda();
        Item_venda iv4 = new Item_venda();
        
        Venda v2 = new Venda();
        
        Item item3 = new Item("Porta Retrato 10x15cm", 7.00, 12.00, LocalDate.now(), 5, 'S', "Porta Retrato 10x15");
        Item item4 = new Item("Porta Retrato 15x21cm", 7.00, 12.00, LocalDate.now(), 5, 'S', "Porta Retrato 15x21");
        
        iv3.setItem(item3);
        iv3.setValor_unitario(15.00);
        iv4.setItem(item4);
        iv4.setValor_unitario(20.00);
        
        v2.getItens_venda().add(iv3);
        v2.getItens_venda().add(iv4);
        v2.setValor_venda(15.00);
        
        gerente.getTransaction().begin();
        
        gerente.persist(item3);
        gerente.persist(item4);
        gerente.persist(iv3);
        gerente.persist(iv4);
        gerente.persist(v2);
        
        
        gerente.getTransaction().commit();
        
        /*
        Exemplo pegando do BANCO
        Usuario doBanco = null;
        gerente.getTransaction().begin();
        doBanco = (Usuario) gerente.find(Usuario.class, 1);
        gerente.getTransaction().commit();
        
        System.out.println(doBanco.getUsuario());
        System.out.println(doBanco.getSenha());
        */
        
        gerente.close();
        fabrica.close();
        
        
    }
}