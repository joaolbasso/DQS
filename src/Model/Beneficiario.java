package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Beneficiario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_beneficiario;
    
    @Column(length = 50, unique = true)
    private String nome_beneficiario;

    public Beneficiario() {
    }

    public Beneficiario(String nome_beneficiario) {
        this.nome_beneficiario = nome_beneficiario;
    }

    public int getId_beneficiario() {
        return id_beneficiario;
    }

    public void setId_beneficiario(int id_beneficiario) {
        this.id_beneficiario = id_beneficiario;
    }

    public String getNome_beneficiario() {
        return nome_beneficiario;
    }

    public void setNome_beneficiario(String nome_beneficiario) {
        this.nome_beneficiario = nome_beneficiario;
    }
    
    
}
