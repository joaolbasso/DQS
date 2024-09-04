package Model;

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
    
}
