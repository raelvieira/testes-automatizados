package br.com.israelvieira.repository;

import br.com.israelvieira.modelo.Pagamento;

import javax.persistence.EntityManager;

public class PagamentoRepository {

    private final EntityManager manager;
    private final GenericRepository<Pagamento> genericRepository;

    public PagamentoRepository(EntityManager manager) {
        this.manager = manager;
        this.genericRepository = new GenericRepository<>(this.manager );
    }

    public void salva(Pagamento pagamento) {
        this.genericRepository.salva(pagamento );
    }

    public Pagamento buscarPorId(Integer id) {
       return this.genericRepository.buscaPorId(Pagamento.class, id);
    }
}
