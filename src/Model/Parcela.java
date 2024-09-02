package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Parcela implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_parcela;
    
    @Column(nullable = false)
    private Double valor_parcela;
    
    private LocalDate data_vencimento;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_venda")
    private Venda venda;
    
    private int numero_parcela;
    
    public Parcela() {
    }

    
    //Parcela única de pagamento a vista
    public Parcela(Double valor_parcela, Venda venda) {
        this.valor_parcela = valor_parcela;
        this.venda = venda;
    }
    
    //Parcelas a prazo
    
    public Parcela(Double valor_parcela, LocalDate data_vencimento, Venda venda, int numero_parcela) {
        this.valor_parcela = valor_parcela;
        this.data_vencimento = data_vencimento;
        this.venda = venda;
        this.numero_parcela = numero_parcela;
    }

    public int getNumero_parcela() {
        return numero_parcela;
    }

    public void setNumero_parcela(int numero_parcela) {
        this.numero_parcela = numero_parcela;
    }
    

    
    
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

    public LocalDate getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(LocalDate data_vencimento) {
        this.data_vencimento = data_vencimento;
    }
    
}