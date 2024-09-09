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
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cliente;
    
    @Column(nullable = false, length = 50)
    private String nome_cliente;
    
    @Column(nullable = false, length = 11)
    private String telefone;
    
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    
    @Column(length = 60)
    private String logradouro;
    @Column(length = 60)
    private String bairro;
    @Column(length = 9)
    private String cep;
    @Column(length = 20)
    private String numero;
    @Column(length = 60)
    private String complemento;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_cidade")
    private Cidade cidade;
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_estado")
    private Estado estado;

    public Cliente() {
    }


    public Cliente(String nome_cliente, String telefone, String cpf, String logradouro, String bairro, String cep, String numero, String complemento, Cidade cidade, Estado estado) {
        this.nome_cliente = nome_cliente;
        this.telefone = telefone;
        this.cpf = cpf;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
