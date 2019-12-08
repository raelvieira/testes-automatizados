package br.com.israelvieira.repository;

import br.com.israelvieira.builder.EmprestimoBuilder;
import br.com.israelvieira.modelo.Emprestimo;
import br.com.israelvieira.modelo.Pagamento;
import br.com.israelvieira.modelo.TipoPagamento;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class PagamentoRepositoryTest {
    private EntityManager manager;
    private static EntityManagerFactory emf;
    private PagamentoRepository pagamentoRepositorio;
    private EmprestimoRepository emprestimoRepository;

    @BeforeAll
    public static void inicio() {
        emf = Persistence.createEntityManagerFactory("emprestimos");
    }

    @BeforeEach
    public void antes() {
        manager = emf.createEntityManager();
        manager.getTransaction().begin();

        pagamentoRepositorio = new PagamentoRepository(manager);
        emprestimoRepository = new EmprestimoRepository(manager );
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
    public void deveSalvarUmNovoPagamento(){
        Emprestimo emprestimo = EmprestimoBuilder.umEmprestimo().constroi();
        emprestimoRepository.salva(emprestimo);

        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento(TipoPagamento.DINHEIRO);
        pagamento.setValorPago(new BigDecimal(3.9));
        pagamento.setEmprestimo(emprestimo);

        pagamentoRepositorio.salva(pagamento);

        Pagamento pagamentoBancoDeDados = pagamentoRepositorio.buscarPorId(pagamento.getId());

        Assertions.assertNotNull(pagamentoBancoDeDados);
    }
}
