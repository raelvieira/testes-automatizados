package br.com.israelvieira.repository;

import br.com.israelvieira.builder.EmprestimoBuilder;
import br.com.israelvieira.builder.LivroBuilder;
import br.com.israelvieira.modelo.Emprestimo;
import br.com.israelvieira.modelo.Livro;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory emf;
    private EmprestimoRepository repositorioEmprestimo;
    private LivroRepository repositorioLivro;

    @BeforeAll
    public static void inicio() {
        emf = Persistence.createEntityManagerFactory("emprestimos");
    }

    @BeforeEach
    public void antes() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        repositorioEmprestimo = new EmprestimoRepository(manager);
        repositorioLivro = new LivroRepository(manager);
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
    public void deveTrazerListaComLivrosEmprestados(){
        Livro livro1 = LivroBuilder.umLivro().emprestado().constroi();
        Livro livro2 = LivroBuilder.umLivro().emprestado().comTitulo("Tubarão").constroi();

        Emprestimo emprestimo1 = EmprestimoBuilder.umEmprestimo().comLivro(livro1).constroi();
        Emprestimo emprestimo2 = EmprestimoBuilder.umEmprestimo().comLivro(livro2).constroi();

        repositorioLivro.salva(livro1);
        repositorioLivro.salva(livro2);
        repositorioEmprestimo.salva(emprestimo1);
        repositorioEmprestimo.salva(emprestimo2);

        List<Emprestimo> listaEmprestimosNaValidade = repositorioEmprestimo.livrosEmEmprestimo();

        Assertions.assertEquals(2, listaEmprestimosNaValidade.size() );
    }

    @Test
    public void deveTrazerListaComLivrosAtrasados(){
        Livro livro1 = LivroBuilder.umLivro().comTitulo("O Empréstimo").emprestimoAtrasadoUmDia().constroi();
        Livro livro2 = LivroBuilder.umLivro().comTitulo("O Procurado").emprestimoAtrasadoUmDia().constroi();

        Emprestimo emprestimo1 = EmprestimoBuilder.umEmprestimo().comDataRetorno(LocalDate.now().minusDays(1)).comLivro(livro1).constroi();
        Emprestimo emprestimo2 = EmprestimoBuilder.umEmprestimo().comDataRetorno(LocalDate.now().minusDays(1)).comLivro(livro2).constroi();

        repositorioLivro.salva(livro1);
        repositorioLivro.salva(livro2);
        repositorioEmprestimo.salva(emprestimo1);
        repositorioEmprestimo.salva(emprestimo2);

        List<Emprestimo> listaEmprestimosComLivrosAtrasados = repositorioEmprestimo.atrasadosParaDevolucao();

        Assertions.assertEquals(2, listaEmprestimosComLivrosAtrasados.size() );
    }
}
