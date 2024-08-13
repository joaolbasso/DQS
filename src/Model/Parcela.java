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

/**
 *
 * @author VIDEO
 */
@Entity
public class Parcela implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_parcela;
    
    @Column(nullable = false)
    private Double valor_parcela;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private LocalDate data_vencimento;
    private char metodo_pagamento;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_venda")
    private Venda venda;

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
    
    public int getId_parcela() {
        return id_parcela;
    }

    public void setId_parcela(int id_parcela) {
        this.id_parcela = id_parcela;
    }

    public Double getValor_parcela() {
        return valor_parcela;
    }

    public void setValor_parcela(Double valor_parcela) {
        this.valor_parcela = valor_parcela;
    }

    public LocalDate getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(LocalDate data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public char getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(char metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }
    
    
    
}
