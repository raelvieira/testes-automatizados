package br.com.israelvieira.teste;

import br.com.israelvieira.modelo.Usuario;
import br.com.israelvieira.util.EMFactory;

import javax.persistence.EntityManager;

public class Main {

    public static void main(String[] args) {
        Usuario usuario = new Usuario();
        usuario.setMatricula("5a6q5");
        usuario.setNome("Israel Vieira");
        EntityManager em = new EMFactory().getEntityManager();

        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();

        em.close();
    }

}
