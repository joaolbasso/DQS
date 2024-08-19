package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public abstract class AbstractDAO {
    protected EntityManagerFactory emf;
    protected EntityManager em;
}
