package Model;

import java.util.Date;

/**
 *
 * @author JOAO
 */
public class Caixa {
    
    private int id_caixa;
    private Date data_hora_abertura;
    private Date data_hora_fechamento;

    public int getId_caixa() {
        return id_caixa;
    }

    public void setId_caixa(int id_caixa) {
        this.id_caixa = id_caixa;
    }

    public Date getData_hora_abertura() {
        return data_hora_abertura;
    }

    public void setData_hora_abertura(Date data_hora_abertura) {
        this.data_hora_abertura = data_hora_abertura;
    }

    public Date getData_hora_fechamento() {
        return data_hora_fechamento;
    }

    public void setData_hora_fechamento(Date data_hora_fechamento) {
        this.data_hora_fechamento = data_hora_fechamento;
    }

}
