package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class Caixa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_caixa;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data_hora_abertura;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data_hora_fechamento;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario_que_abriu;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_item_caixa")
    private ArrayList<Item_caixa> itens_caixa;

    public ArrayList<Item_caixa> getItens_caixa() {
        return itens_caixa;
    }

    public void setItens_caixa(ArrayList<Item_caixa> itens_caixa) {
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

    public Date getData_hora_abertura() {
        return data_hora_abertura;
    }

    public void setData_hora_abertura(Date data_hora_abertura) {
        this.data_hora_abertura = data_hora_abertura;
    }

    public Date getData_hora_fechamento() {
        return data_hora_fechamento;
    }

    public void setData_hora_fechamento(Date data_hora_fechamento) {
        this.data_hora_fechamento = data_hora_fechamento;
    }

}
