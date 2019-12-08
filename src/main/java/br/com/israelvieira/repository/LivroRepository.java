package br.com.israelvieira.repository;

import br.com.israelvieira.modelo.Emprestimo;
import br.com.israelvieira.modelo.Livro;

import javax.persistence.EntityManager;
import java.util.List;

public class LivroRepository {
    private final EntityManager manager;
    private final GenericRepository<Livro> genericRepository;

    public LivroRepository(EntityManager manager) {
        this.manager = manager;
        this.genericRepository = new GenericRepository<>(this.manager );
    }

    public Livro buscaPor(Integer id) {
        return this.genericRepository.buscaPorId(Livro.class, id);
    }

    public void salva(Livro livro) {
        this.genericRepository.salva(livro );
    }

    public void atualiza(Livro livro) {
        manager.merge(livro );
    }

    public List<Emprestimo> historicoDeEmprestimosDo(Livro livro) {
        livro = this.buscaPor(livro.getId());

        return livro.getHistorico();
    }

    public Livro buscaPorTitulo(String titulo) {
        return manager.createQuery("SELECT l FROM Livro l WHERE l.titulo = :Titulo", Livro.class)
                .setParameter("Titulo", titulo)
                .getSingleResult();
    }
}
