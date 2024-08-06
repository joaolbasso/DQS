package Model;

import java.util.Date;

/**
 *
 * @author VIDEO
 */
public class Parcela {
    private int id_parcela;
    private Double valor_parcela;
    private Date data_vencimento;
    private String metodo_pagamento;
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

    public Date getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(Date data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public String getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(String metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }
    
    
    
}
