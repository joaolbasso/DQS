package Model;

import DAO.CaixaDAO;
import DAO.Item_caixaDAO;
import DAO.UsuarioDAO;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
public class Caixa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_caixa;
    
    @Column(name = "data_hora_abertura", nullable = false)
    private LocalDateTime data_hora_abertura;
    
    private LocalDateTime data_hora_fechamento;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario_que_abriu;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCaixa estado_caixa;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "ITENS_CAIXA", joinColumns = @JoinColumn(name = "id_caixa"), 
            inverseJoinColumns = @JoinColumn(name = "id_item_caixa"))
    private Collection<Item_caixa> itens_caixa = new ArrayList<Item_caixa>();

    public Caixa() {
        this.estado_caixa = StatusCaixa.ABERTO;
        this.data_hora_abertura = LocalDateTime.now();
    }

    public Caixa(Usuario usuario_que_abriu) {
        this.data_hora_abertura = LocalDateTime.now();
        this.usuario_que_abriu = usuario_que_abriu;
        this.estado_caixa = StatusCaixa.ABERTO;
    }
    
    public Collection<Item_caixa> getItens_caixa() {
        return itens_caixa;
    }

    public void setItens_caixa(Collection<Item_caixa> itens_caixa) {
        this.itens_caixa = itens_caixa;
    }

    public Usuario getUsuario_que_abriu() {
        return usuario_que_abriu;
    }

    public void setUsuario_que_abriu(Usuario usuario_que_abriu) {
        this.usuario_que_abriu = usuario_que_abriu;
    }

    public int getId_caixa() {
        return id_caixa;
    }

    public void setId_caixa(int id_caixa) {
        this.id_caixa = id_caixa;
    }

    public LocalDateTime getData_hora_abertura() {
        return data_hora_abertura;
    }

    public void setData_hora_abertura(LocalDateTime data_hora_abertura) {
        this.data_hora_abertura = data_hora_abertura;
    }

    public LocalDateTime getData_hora_fechamento() {
        return data_hora_fechamento;
    }

    public void setData_hora_fechamento(LocalDateTime data_hora_fechamento) {
        this.data_hora_fechamento = data_hora_fechamento;
    }

    public StatusCaixa getEstado_caixa() {
        return estado_caixa;
    }

    public void setEstado_caixa(StatusCaixa estado_caixa) {
        this.estado_caixa = estado_caixa;
    }
    
    public enum StatusCaixa {
    ABERTO,
    FECHADO
    }
    
    public Caixa abrirCaixa() {
        CaixaDAO caixaDAO = new CaixaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Item_caixaDAO item_caixaDAO = new Item_caixaDAO();
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
        
        Caixa caixaNovo = new Caixa(usuario);
        caixaDAO.insert(caixaNovo);
        Item_caixa item_aporte = new Item_caixa(valor, LocalDate.now(), Item_caixa.TipoOperacao.A, caixaNovo, 'A', "Aporte Inicial");
        
        caixaNovo.getItens_caixa().add(item_aporte);
        
        item_caixaDAO.insert(item_aporte);
        
        caixaDAO.update(caixaNovo);
        
        return caixaNovo;
    }
}
