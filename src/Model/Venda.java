package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
public class Venda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_venda;
    
    @Column(nullable = false)
    private Double valor_venda;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data_venda;
    private Cliente cliente;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_item_venda")
    private ArrayList<Item_venda> itens_venda;

    public ArrayList<Item_venda> getItens_venda() {
        return itens_venda;
    }

    public void setItens_venda(ArrayList<Item_venda> itens_venda) {
        this.itens_venda = itens_venda;
    }
    
    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public Double getValor_venda() {
        return valor_venda;
    }

    public void setValor_venda(Double valor_venda) {
        this.valor_venda = valor_venda;
    }

    public Date getData_venda() {
        return data_venda;
    }

    public void setData_venda(Date data_venda) {
        this.data_venda = data_venda;
    }
    
    
}
