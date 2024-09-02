package Model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Item_venda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_item_venda;
    
    private int quantidade;
    
    @Column(nullable = false)
    private Double valor_unitario;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_item")
    private Item item; //Serviço ou Produto

    public Item_venda() {
    }

    public Item_venda(int quantidade, Double valor_unitario, Item item) {
        this.quantidade = quantidade;
        this.valor_unitario = valor_unitario;
        this.item = item;
    }
    
    public int getId_item_venda() {
        return id_item_venda;
    }

    public void setId_item_venda(int id_item_venda) {
        this.id_item_venda = id_item_venda;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor_unitario() {
        return this.getQuantidade() * this.item.getValor_item();
    }

    public void setValor_unitario(Double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }
    
}