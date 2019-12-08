package br.com.israelvieira.repository;

import br.com.israelvieira.modelo.Emprestimo;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoRepository {
    private final EntityManager manager;
    private final GenericRepository<Emprestimo> genericRepository;

    public EmprestimoRepository(EntityManager manager) {
        this.manager = manager;
        this.genericRepository = new GenericRepository<>(this.manager );
    }

    public void salva(Emprestimo emprestimo) {
        this.genericRepository.salva(emprestimo );
    }

    public List<Emprestimo> livrosEmEmprestimo() {

        return manager
                .createQuery("from Emprestimo e where e.livroLocado.isEmprestado = :isEmprestado", Emprestimo.class)
                .setParameter("isEmprestado", true)
                .getResultList();

    }

    public List<Emprestimo> atrasadosParaDevolucao() {

        return manager
                .createQuery("from Emprestimo e where e.dataPrevista < :dataAtual", Emprestimo.class)
                .setParameter("dataAtual", LocalDate.now())
                .getResultList();

    }
}
