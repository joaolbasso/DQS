package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Venda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_venda;
    
    @Column(nullable = false)
    private Double valor_venda;
    
    private LocalDate data_venda;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "ITENS_VENDA", joinColumns = @JoinColumn(name = "id_venda"),
            inverseJoinColumns = @JoinColumn(name = "id_item_venda"))
    private Collection<Item_venda> itens_venda = new ArrayList<Item_venda>();

    public Venda(Double valor_venda, LocalDate data_venda, Cliente cliente, ArrayList<Item_venda> itensDaVenda) {
        this.valor_venda = valor_venda;
        this.data_venda = data_venda;
        this.cliente = cliente;
        this.itens_venda = itensDaVenda;
    }
    
    public Collection<Item_venda> getItens_venda() {
        return itens_venda;
    }

    public void setItens_venda(Collection<Item_venda> itens_venda) {
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

    public LocalDate getData_venda() {
        return data_venda;
    }

    public void setData_venda(LocalDate data_venda) {
        this.data_venda = data_venda;
    }
    
    
}
