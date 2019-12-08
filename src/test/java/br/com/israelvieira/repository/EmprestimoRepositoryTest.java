package br.com.israelvieira.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EmprestimoRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory emf;
    private EmprestimoRepository repositorio;

    @BeforeEach
    public void antes() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        repositorio = new EmprestimoRepository(manager);
    }

    @AfterEach
    public void depois() {
        manager.getTransaction().rollback();
    }

    @AfterAll
    public static void fim() {
        emf.close();
    }

    @Test
    public void deveSalvarUmNovoEmprestimo(){

    }
}
