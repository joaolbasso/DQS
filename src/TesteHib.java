import DAO.BeneficiarioDAO;
import DAO.CidadeDAO;
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
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("DQSPU");
        EntityManager gerente = fabrica.createEntityManager();
        /*
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
        
        
        
        gerente.persist(item3);
        gerente.persist(item4);
        gerente.persist(iv3);
        gerente.persist(iv4);
        gerente.persist(v2);
        
        
        
        
        
        
            BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
            List<Beneficiario> benefiL = beneficiarioDAO.todosOsBeneficiarios();
            Beneficiario bene = benefiL.get(2);
            Despesa despesa = new Despesa("Linguiça", 41.00, "Linguiça do pote", 0,bene);
            gerente.persist(despesa);
         
        
        gerente.getTransaction().begin();
        
        Despesa edit = gerente.find(Despesa.class, 6);
        edit.setValor_despesa(100.00);
        gerente.merge(edit);
        
        
        Despesa delete = gerente.find(Despesa.class, 7);
        gerente.remove(delete);
        
        
        
        gerente.getTransaction().commit();
        
            CidadeDAO cidadeDAO = new CidadeDAO();
            Cidade cidade = cidadeDAO.buscaCidadePorId(348);
            Cliente cliente = new Cliente("Joao Leonardo Basso", "42999406830", "10086858990", "Rua Indios do Brasil", "Vila Nova", "84530000", "284", "Casa", cidade);
            gerente.persist(cliente);
        
        
        
        
        //Exemplo pegando do BANCO
        Usuario doBanco = null;
        gerente.getTransaction().begin();
        doBanco = (Usuario) gerente.find(Usuario.class, 1);
        gerente.getTransaction().commit();
        
        System.out.println(doBanco.getUsuario());
        System.out.println(doBanco.getSenha());
        
        
        gerente.close();
        fabrica.close();
        */
    }
}