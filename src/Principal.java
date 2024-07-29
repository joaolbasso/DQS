
import Model.usuarioTeste;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author zombi
 */
public class Principal {
    public static void main(String[] args) {
        EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("DQS");
        EntityManager gerente = fabrica.createEntityManager();
        
        usuarioTeste usuario = new usuarioTeste("vinicius");
        gerente.getTransaction().begin();
        gerente.persist(usuario);
        gerente.getTransaction().commit();
    }
}
