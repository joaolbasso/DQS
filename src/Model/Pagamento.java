package Model;

import java.util.Date;

/**
 *
 * @author VIDEO
 */
public class Pagamento {
    private int id_pagamento;
    private char tipo_recebimento;
    private String metodo_pagamento;
    private Date data_pagamento;
    private Double valor_pagamento;

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

    public String getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(String metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }

    public Date getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(Date data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public Double getValor_pagamento() {
        return valor_pagamento;
    }

    public void setValor_pagamento(Double valor_pagamento) {
        this.valor_pagamento = valor_pagamento;
    }
    
    
}
