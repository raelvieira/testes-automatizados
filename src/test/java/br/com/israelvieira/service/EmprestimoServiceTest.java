package br.com.israelvieira.service;

import br.com.israelvieira.builder.EmprestimoBuilder;
import br.com.israelvieira.builder.LivroBuilder;
import br.com.israelvieira.infra.Email;
import br.com.israelvieira.modelo.Emprestimo;
import br.com.israelvieira.modelo.Livro;
import br.com.israelvieira.modelo.Usuario;
import br.com.israelvieira.repository.EmprestimoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.LimitExceededException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

class EmprestimoServiceTest {
    private EmprestimoService emprestimoService;
    private Usuario usuario;

    @BeforeEach
    public void setup() {
        emprestimoService = new EmprestimoService();
        usuario = new Usuario("Fulado de tal", "M40E");
    }

    @Test
    public void deveRetornarEmprestimosAtrasados() {
        Emprestimo emprestimo1 = EmprestimoBuilder.umEmprestimo().emAtraso().constroi();
        Emprestimo emprestimo2 = EmprestimoBuilder.umEmprestimo().emAtraso().constroi();

        EmprestimoRepository repositoryFalso = mock(EmprestimoRepository.class);
        Email emailFalso = mock(Email.class);

        when(repositoryFalso.atrasadosParaDevolucao()).thenReturn(Arrays.asList(emprestimo1, emprestimo2));

        EmprestimoService service = new EmprestimoService(emailFalso, repositoryFalso);
        List<Emprestimo> emprestimos = service.verificarEmprestimosAtrasados();

        Assertions.assertNotNull(emprestimos);
        Assertions.assertEquals(2, emprestimos.size());
    }

    @Test
    public void deveContinuarEnviarEmailMesmoComException() {
        Emprestimo emprestimo1 = EmprestimoBuilder.umEmprestimo().emAtraso().constroi();
        Emprestimo emprestimo2 = EmprestimoBuilder.umEmprestimo().emAtraso().constroi();

        EmprestimoRepository repositoryFalso = mock(EmprestimoRepository.class);
        Email emailFalso = mock(Email.class);

        when(repositoryFalso.atrasadosParaDevolucao()).thenReturn(Arrays.asList(emprestimo1, emprestimo2));
        doThrow(new RuntimeException()).when(emailFalso).envia(emprestimo1);

        EmprestimoService service = new EmprestimoService(emailFalso, repositoryFalso);
        service.verificarEmprestimosAtrasados();

        verify(emailFalso, times(1)).envia(emprestimo2);
    }

    @Test
    public void deveEmprestarLivroNaoReservado() throws LimitExceededException {
        Livro livro = LivroBuilder.umLivro().constroi();
        Emprestimo emprestimo = emprestimoService.emprestarLivro(usuario, livro);

        assertThat(emprestimo.getDataEmprestimo().equals(LocalDate.now()), is(true));
        assertThat(emprestimo.getDataPrevista().equals(LocalDate.now().plusDays(7)), is(true));
    }

    @Test
    public void NaoDeveEmprestarLivroReservado() {
        Livro livro = LivroBuilder.umLivro().reservado().constroi();

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> emprestimoService.emprestarLivro(usuario, livro),
                "Deveria ter lançado um RuntimeException");

        assertTrue(exception.getMessage().contains("Livro indisponível"));
    }

    @Test
    public void NaoDeverEmprestarLivroParaUsuarioComDoisLivros() throws LimitExceededException {
        Livro livro = LivroBuilder.umLivro().constroi();
        emprestimoService.inicializaListaParaDoisEmprestimos(livro);

        LimitExceededException exception = assertThrows(
            LimitExceededException.class,
            () -> emprestimoService.emprestarLivro(usuario, livro),
            "Deveria ter lançado um LimitExceededException"
        );

        assertTrue(exception.getMessage().contains("O usuário excedeu o limite de empréstimos!"));
    }

    @Test
    public void retornaListaEmprestimosDoUsuarioVazia() {
        Livro livro = LivroBuilder.umLivro().constroi();
        List<Emprestimo> emprestimosRealizados = emprestimoService.consultarEmprestimosPorUsuario(usuario);

        Assertions.assertNotNull(emprestimosRealizados);
        Assertions.assertEquals(0, emprestimosRealizados.size());
    }

    @Test
    void retornaListaEmprestimosDoUsuarioTamanhoUm() {
        Livro livro = LivroBuilder.umLivro().constroi();
        emprestimoService.inicializaListaParaUmEmprestimo(livro);

        List<Emprestimo> emprestimosRealizados = emprestimoService.consultarEmprestimosPorUsuario(usuario);

        Assertions.assertNotNull(emprestimosRealizados);
        Assertions.assertEquals(1, emprestimosRealizados.size());
    }

    @Test
    void retornaListaEmprestimosDoUsuarioTamanhoDois() {
        Livro livro = LivroBuilder.umLivro().constroi();
        emprestimoService.inicializaListaParaDoisEmprestimos(livro);

        List<Emprestimo> emprestimosRealizados = emprestimoService.consultarEmprestimosPorUsuario(usuario);

        Assertions.assertNotNull(emprestimosRealizados);
        Assertions.assertEquals(2, emprestimosRealizados.size());
    }

    @Test
    void retornarValorPagoAntesDaData() {
        Livro livro = LivroBuilder.umLivro().emprestado().constroi();

        Assertions.assertEquals(new BigDecimal(5), emprestimoService.devolverLivro(livro));
    }

    @Test
    void retornarValorPagoComMultaUmDia() {
        Livro livro = LivroBuilder.umLivro().emprestimoAtrasadoUmDia().constroi();

        Assertions.assertEquals(new BigDecimal(5.40).doubleValue(),
                emprestimoService.devolverLivro(livro).doubleValue(),
                0.00001
                );
    }

    @Test
    void retornarValorPagoComMultaTrintaDias() {
        Livro livro = LivroBuilder.umLivro().emprestimoAtrasadoTrintaDias().constroi();

        Assertions.assertEquals(new BigDecimal(7.75).doubleValue(),
                emprestimoService.devolverLivro(livro).doubleValue(),
                0.00001
        );
    }
}