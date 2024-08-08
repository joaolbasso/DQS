package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_item;
    
    @Column(nullable = false)
    private String nome_item;
    private String descricao_item;
    
    @Column(nullable = false)
    private Double valor_item;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data_preco_item;
    private int quantidade;
    private Double preco_custo_item;
    private char tipo_item;

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getNome_item() {
        return nome_item;
    }

    public void setNome_item(String nome_item) {
        this.nome_item = nome_item;
    }

    public String getDescricao_item() {
        return descricao_item;
    }

    public void setDescricao_item(String descricao_item) {
        this.descricao_item = descricao_item;
    }

    public Double getValor_item() {
        return valor_item;
    }

    public void setValor_item(Double valor_item) {
        this.valor_item = valor_item;
    }

    public Date getData_preco_item() {
        return data_preco_item;
    }

    public void setData_preco_item(Date data_preco_item) {
        this.data_preco_item = data_preco_item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco_custo_item() {
        return preco_custo_item;
    }

    public void setPreco_custo_item(Double preco_custo_item) {
        this.preco_custo_item = preco_custo_item;
    }

    public char getTipo_item() {
        return tipo_item;
    }

    public void setTipo_item(char tipo_item) {
        this.tipo_item = tipo_item;
    }
    
    
}
