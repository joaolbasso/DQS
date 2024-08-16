
package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Estado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_estado;
    
    @Column(nullable = false, unique = true, length = 50)
    private String nome_estado;
    
    @Column(nullable = false, length = 2, unique = true)
    private String sigla_uf;

    public Estado() {
        
    }

    public Estado(int id_estado, String nome_estado, String sigla_uf) {
        this.id_estado = id_estado;
        this.nome_estado = nome_estado;
        this.sigla_uf = sigla_uf;
    }
    
    public Estado(String nome_estado, String sigla_uf) {
        this.nome_estado = nome_estado;
        this.sigla_uf = sigla_uf;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public String getNome_estado() {
        return nome_estado;
    }

    public void setNome_estado(String nome_estado) {
        this.nome_estado = nome_estado;
    }

    public String getSigla_uf() {
        return sigla_uf;
    }

    public void setSigla_uf(String sigla_uf) {
        this.sigla_uf = sigla_uf;
    }
    
    
}
