package Model;

import java.util.Date;

public class Item_caixa {
    private int id_item_caixa;
    private Double valor;
    private Date data_hota;
    private String descricao;
    private char tipo_operacao;
    private String metodo_pagamento;
    private Caixa caixa;
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

    public Date getData_hota() {
        return data_hota;
    }

    public void setData_hota(Date data_hota) {
        this.data_hota = data_hota;
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
