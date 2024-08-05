package Model;

import java.util.ArrayList;

/**
 *
 * @author VIDEO
 */
public class Item_venda {
    private int id_item_venda;
    private int quantidade;
    private Double valor_unitario;
    private ArrayList<Item> itens_venda;

    public ArrayList<Item> getItens_venda() {
        return itens_venda;
    }

    public void setItens_venda(ArrayList<Item> itens_venda) {
        this.itens_venda = itens_venda;
    }
    
    

    public int getId_item_venda() {
        return id_item_venda;
    }

    public void setId_item_venda(int id_item_venda) {
        this.id_item_venda = id_item_venda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(Double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }
    
    
    
}
