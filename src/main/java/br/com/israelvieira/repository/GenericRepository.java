package br.com.israelvieira.repository;

import javax.persistence.EntityManager;

class GenericRepository<T> {

    private final EntityManager manager;

    GenericRepository(EntityManager manager) {
        this.manager = manager;
    }

    void salva(T t) {
        this.manager.persist(t );
    }

    T buscaPorId(Class<T> classe, Integer id) {
        return this.manager.find(classe, id);
    }
}