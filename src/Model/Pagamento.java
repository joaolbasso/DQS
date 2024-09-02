package Model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Pagamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pagamento;
    private char tipo_recebimento;
    private char metodo_pagamento;
    
    private LocalDate data_pagamento;
    
    @Column(nullable = false)
    private Double valor_pagamento;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_parcela")
    private Parcela parcela;

    public Pagamento() {
    }

    //Pagamento a vista

    public Pagamento(char tipo_recebimento, char metodo_pagamento, LocalDate data_pagamento, Double valor_pagamento, Parcela parcela) {
        this.tipo_recebimento = tipo_recebimento;
        this.metodo_pagamento = metodo_pagamento;
        this.data_pagamento = data_pagamento;
        this.valor_pagamento = valor_pagamento;
        this.parcela = parcela;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }
    
    public int getId_pagamento() {
        return id_pagamento;
    }

    public void setId_pagamento(int id_pagamento) {
        this.id_pagamento = id_pagamento;
    }

    public char getTipo_recebimento() {
        return tipo_recebimento;
    }

    public void setTipo_recebimento(char tipo_recebimento) {
        this.tipo_recebimento = tipo_recebimento;
    }

    public char getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(char metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }

    public LocalDate getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(LocalDate data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public Double getValor_pagamento() {
        return valor_pagamento;
    }

    public void setValor_pagamento(Double valor_pagamento) {
        this.valor_pagamento = valor_pagamento;
    }
    
    
}