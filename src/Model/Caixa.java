package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
    
    private LocalDate data_hora_abertura;
    
    private LocalDate data_hora_fechamento;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario_que_abriu;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "ITENS_CAIXA", joinColumns = @JoinColumn(name = "id_caixa"), 
            inverseJoinColumns = @JoinColumn(name = "id_item_caixa"))
    private Collection<Item_caixa> itens_caixa = new ArrayList<Item_caixa>();

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

    public LocalDate getData_hora_abertura() {
        return data_hora_abertura;
    }

    public void setData_hora_abertura(LocalDate data_hora_abertura) {
        this.data_hora_abertura = data_hora_abertura;
    }

    public LocalDate getData_hora_fechamento() {
        return data_hora_fechamento;
    }

    public void setData_hora_fechamento(LocalDate data_hora_fechamento) {
        this.data_hora_fechamento = data_hora_fechamento;
    }

}
