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
    private int recorrencia;
    private Date data_vencimento;
    private Date data_pagamento;

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
        return recorrencia;
    }

    public void setRecorrencia(int recorrencia) {
        this.recorrencia = recorrencia;
    }

    public Date getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(Date data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public Date getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(Date data_pagamento) {
        this.data_pagamento = data_pagamento;
    }
    
    
}
