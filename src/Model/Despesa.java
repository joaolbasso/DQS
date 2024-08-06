package Model;

import java.util.Date;

/**
 *
 * @author VIDEO
 */

public class Despesa {
    private int id_despesa;
    private Double valor_despesa;
    private String descricao_despesa;
    private int recorrencia_despesa;
    private Date data_vencimento_despesa;
    private Date data_pagamento_despesa;
    private Beneficiario beneficiario;
    private Item_caixa item_caixa;

    public int getRecorrencia_despesa() {
        return recorrencia_despesa;
    }

    public void setRecorrencia_despesa(int recorrencia_despesa) {
        this.recorrencia_despesa = recorrencia_despesa;
    }

    public Date getData_vencimento_despesa() {
        return data_vencimento_despesa;
    }

    public void setData_vencimento_despesa(Date data_vencimento_despesa) {
        this.data_vencimento_despesa = data_vencimento_despesa;
    }

    public Date getData_pagamento_despesa() {
        return data_pagamento_despesa;
    }

    public void setData_pagamento_despesa(Date data_pagamento_despesa) {
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
    
    
    
    public int getId_despesa() {
        return id_despesa;
    }

    public void setId_despesa(int id_despesa) {
        this.id_despesa = id_despesa;
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

    public int getRecorrencia() {
        return recorrencia_despesa;
    }

    public void setRecorrencia(int recorrencia) {
        this.recorrencia_despesa = recorrencia;
    }

    public Date getData_vencimento() {
        return data_vencimento_despesa;
    }

    public void setData_vencimento(Date data_vencimento) {
        this.data_vencimento_despesa = data_vencimento;
    }

    public Date getData_pagamento() {
        return data_pagamento_despesa;
    }

    public void setData_pagamento(Date data_pagamento) {
        this.data_pagamento_despesa = data_pagamento;
    }
    
    
}
