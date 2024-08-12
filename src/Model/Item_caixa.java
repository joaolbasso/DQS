package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class Item_caixa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_item_caixa;
    
    @Column(nullable = false)
    private Double valor;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate data_hora;
    
    private String descricao;
    private char tipo_operacao;
    private String metodo_pagamento;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_caixa")
    private Caixa caixa;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pagamento")
    private Pagamento pagamento;

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getData_hora() {
        return data_hora;
    }

    public void setData_hora(LocalDate data_hora) {
        this.data_hora = data_hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public char getTipo_operacao() {
        return tipo_operacao;
    }

    public void setTipo_operacao(char tipo_operacao) {
        this.tipo_operacao = tipo_operacao;
    }

    public String getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(String metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }
    
    
}
