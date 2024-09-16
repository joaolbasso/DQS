package Model;
//Ok DIAGs
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
public class Despesa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_despesa;

    @Column(nullable = false)
    private String nome_despesa;
    
    @Column(nullable = false)
    private Double valor_despesa;
    
    private String descricao_despesa;
    private int dia_pagamento_despesa;
    
    private LocalDate data_vencimento_despesa;
    
    private LocalDate data_pagamento_despesa;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_beneficiario")
    private Beneficiario beneficiario;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_item_caixa")
    private Item_caixa item_caixa;

    public Despesa() {
    }

    public Despesa(String nome_despesa, Double valor_despesa, String descricao_despesa, int dia_pagamento_despesa, LocalDate data_vencimento_despesa, LocalDate data_pagamento_despesa) {
        this.nome_despesa = nome_despesa;
        this.valor_despesa = valor_despesa;
        this.descricao_despesa = descricao_despesa;
        this.dia_pagamento_despesa = dia_pagamento_despesa;
        this.data_vencimento_despesa = data_vencimento_despesa;
        this.data_pagamento_despesa = data_pagamento_despesa;
    }

    public Despesa(String nome_despesa, Double valor_despesa, String descricao_despesa, int recorrencia_despesa, LocalDate data_vencimento_despesa, LocalDate data_pagamento_despesa, Beneficiario beneficiario) {
        this(nome_despesa, valor_despesa, descricao_despesa, recorrencia_despesa, data_vencimento_despesa, data_pagamento_despesa);
        this.beneficiario = beneficiario;
    }

    public Despesa(String nome_despesa, Double valor_despesa, String descricao_despesa, int recorrencia_despesa, LocalDate data_vencimento_despesa, LocalDate data_pagamento_despesa, Beneficiario beneficiario, Item_caixa item_caixa) {
        this(nome_despesa, valor_despesa, descricao_despesa, recorrencia_despesa, data_vencimento_despesa, data_pagamento_despesa, beneficiario);
        this.item_caixa = item_caixa;
    }

    public int getId_despesa() {
        return id_despesa;
    }

    public void setId_despesa(int id_despesa) {
        this.id_despesa = id_despesa;
    }

    public String getNome_despesa() {
        return nome_despesa;
    }

    public void setNome_despesa(String nome_despesa) {
        this.nome_despesa = nome_despesa;
    }

    public Double getValor_despesa() {
        return valor_despesa;
    }

    public void setValor_despesa(Double valor_despesa) {
        this.valor_despesa = valor_despesa;
    }

    public String getDescricao_despesa() {
        return descricao_despesa;
    }

    public void setDescricao_despesa(String descricao_despesa) {
        this.descricao_despesa = descricao_despesa;
    }

    public int getDia_pagamento_despesa() {
        return dia_pagamento_despesa;
    }

    public void setDia_pagamento_despesa(int dia_pagamento_despesa) {
        this.dia_pagamento_despesa = dia_pagamento_despesa;
    }

    public LocalDate getData_vencimento_despesa() {
        return data_vencimento_despesa;
    }

    public void setData_vencimento_despesa(LocalDate data_vencimento_despesa) {
        this.data_vencimento_despesa = data_vencimento_despesa;
    }

    public LocalDate getData_pagamento_despesa() {
        return data_pagamento_despesa;
    }

    public void setData_pagamento_despesa(LocalDate data_pagamento_despesa) {
        this.data_pagamento_despesa = data_pagamento_despesa;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Item_caixa getItem_caixa() {
        return item_caixa;
    }

    public void setItem_caixa(Item_caixa item_caixa) {
        this.item_caixa = item_caixa;
    }
    
    public boolean isPago() {
        return data_pagamento_despesa != null;
    }
}
