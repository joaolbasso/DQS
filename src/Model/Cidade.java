package Model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Cidade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cidade;
    
    @Column(nullable = false)
    private String nome_cidade;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_estado")
    private Estado estado;

    public Cidade(int id_cidade, String nome_cidade, Estado estado) {
        this.id_cidade = id_cidade;
        this.nome_cidade = nome_cidade;
        this.estado = estado;
    }

    
    
    public int getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(int id_cidade) {
        this.id_cidade = id_cidade;
    }

    public String getNome_cidade() {
        return nome_cidade;
    }

    public void setNome_cidade(String nome_cidade) {
        this.nome_cidade = nome_cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
}
