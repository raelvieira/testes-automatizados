package br.com.israelvieira.repository;

import br.com.israelvieira.builder.LivroBuilder;
import br.com.israelvieira.modelo.Emprestimo;
import br.com.israelvieira.modelo.Livro;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LivroRepositoryTest {

    private EntityManager manager;
    private static EntityManagerFactory emf;
    private LivroRepository repositorio;

    @BeforeAll
    public static void inicio() {
        emf = Persistence.createEntityManagerFactory("emprestimos");
    }

    @BeforeEach
    public void antes() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        repositorio = new LivroRepository(manager);
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
    public void deveEncontrarLivroPeloTitulo() {

        Assertions.assertThrows(NoResultException.class,
                () -> repositorio.buscaPorTitulo("Harry Potter"),
                "Deveria lançar a exceção NoResultException");

        repositorio.salva(LivroBuilder.umLivro().comTitulo("Harry Potter").constroi());
        manager.flush();
        manager.clear();

        Livro livroDoBanco = repositorio.buscaPorTitulo("Harry Potter");

        assertThat("Harry Potter", is(equalTo(livroDoBanco.getTitulo())));
    }

    @Test
    public void deveAlterarUmLivro() {
        Livro livro = LivroBuilder.umLivro().comTitulo("Batman").constroi();

        repositorio.salva(livro);
        livro.setTitulo("Batman Begins");

        repositorio.atualiza(livro);

        manager.flush();
        manager.clear();

        Livro novoLivro = repositorio.buscaPorTitulo("Batman Begins" );
        Assertions.assertNotNull(novoLivro);

        Assertions.assertThrows(NoResultException.class,
                () -> repositorio.buscaPorTitulo("Batman"),
                "Deveria ter lançado a exceção NoResultException");
    }

    @Test
    public void deveRetornarHistoricoEmprestimos() {
        Livro livro = LivroBuilder.umLivro().comTitulo("Avengers").constroi();
        repositorio.salva(livro);

        manager.flush();
        manager.clear();

        Livro livroRepositorio = repositorio.buscaPorTitulo("Avengers");
        Assertions.assertNotNull(livroRepositorio);

        List<Emprestimo> historico = repositorio.historicoDeEmprestimosDo(livroRepositorio);
        Assertions.assertNotNull(historico);
    }

}
