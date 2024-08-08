package Model;

import java.util.ArrayList;
import java.util.Date;

public class Venda {
    private int id_venda;
    private Double valor_venda;
    private Date data_venda;
    private Cliente cliente;
    private ArrayList<Item_venda> itens_venda;

    public ArrayList<Item_venda> getItens_venda() {
        return itens_venda;
    }

    public void setItens_venda(ArrayList<Item_venda> itens_venda) {
        this.itens_venda = itens_venda;
    }
    
    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public Double getValor_venda() {
        return valor_venda;
    }

    public void setValor_venda(Double valor_venda) {
        this.valor_venda = valor_venda;
    }

    public Date getData_venda() {
        return data_venda;
    }

    public void setData_venda(Date data_venda) {
        this.data_venda = data_venda;
    }
    
    
}
