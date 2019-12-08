package br.com.israelvieira.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("emprestimos");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void close() {
        emf.close();
    }
}
