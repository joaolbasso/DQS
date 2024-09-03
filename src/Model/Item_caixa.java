package Model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Item_caixa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_item_caixa;
    
    @Column(nullable = false)
    private Double valor_item_caixa;
    
    private LocalDate data_hora;
    //private String data_hora;
    
    @Column(length = 150)
    private String descricao_item_caixa;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOperacao tipo_operacao;             // a - aporte; d - despesa; s - sangria; v - venda
    
    private char metodo_pagamento;          //c d e p
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_caixa")
    private Caixa caixa;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_pagamento")
    private Pagamento pagamento;
    
    public Item_caixa() {
    }

    public Item_caixa(Double valor_item_caixa, LocalDate data_hora, String descricao_item_caixa, TipoOperacao tipo_operacao, char metodo_pagamento, Caixa caixa, Pagamento pagamento) {
        this.valor_item_caixa = valor_item_caixa;
        this.data_hora = data_hora;
        this.descricao_item_caixa = descricao_item_caixa;
        this.tipo_operacao = tipo_operacao;
        this.metodo_pagamento = metodo_pagamento;
        this.caixa = caixa;
        this.pagamento = pagamento;
    }

    
    
    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    
    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }
    

    public int getId_item_caixa() {
        return id_item_caixa;
    }

    public void setId_item_caixa(int id_item_caixa) {
        this.id_item_caixa = id_item_caixa;
    }

    public LocalDate getData_hora() {
        return data_hora;
    }

    public void setData_hora(LocalDate data_hora) {
        this.data_hora = data_hora;
    }

    public Double getValor_item_caixa() {
        return valor_item_caixa;
    }

    public void setValor_item_caixa(Double valor_item_caixa) {
        this.valor_item_caixa = valor_item_caixa;
    }

    public String getDescricao_item_caixa() {
        return descricao_item_caixa;
    }

    public void setDescricao_item_caixa(String descricao_item_caixa) {
        this.descricao_item_caixa = descricao_item_caixa;
    }

    public TipoOperacao getTipo_operacao() {
        return tipo_operacao;
    }

    public char getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(char metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }
    
    

    public void setTipo_operacao(TipoOperacao tipo_operacao) {
        this.tipo_operacao = tipo_operacao;
    }

    
    public enum TipoOperacao {
        A, D, S, V
    }
    
    
}
