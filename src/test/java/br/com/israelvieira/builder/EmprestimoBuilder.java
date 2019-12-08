package br.com.israelvieira.builder;

import br.com.israelvieira.modelo.Emprestimo;
import br.com.israelvieira.modelo.Livro;
import br.com.israelvieira.modelo.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmprestimoBuilder {

    private Emprestimo emprestimo;

    private EmprestimoBuilder() { }

    public static EmprestimoBuilder umEmprestimo() {
        EmprestimoBuilder builder = new EmprestimoBuilder();

        builder.emprestimo = new Emprestimo(
            UsuarioBuilder.umUsuario().constroi(),
            LivroBuilder.umLivro().constroi()
        );

        builder.emprestimo.setDataEmprestimo(LocalDate.now());
        builder.emprestimo.setDataPrevista(LocalDate.now().plusDays(7));

        return builder;
    }

    public EmprestimoBuilder paraUsuario(Usuario usuario) {
        emprestimo.setUsuario(usuario);
        return this;
    }

    public EmprestimoBuilder comLivro(Livro livro) {
        emprestimo.setLivroLocado(livro);
        return this;
    }

    public EmprestimoBuilder comDataLocacao(LocalDate dataLocacao) {
        emprestimo.setDataEmprestimo(dataLocacao);
        emprestimo.setDataDevolucao(dataLocacao.plusDays(7));
        return this;
    }

    public EmprestimoBuilder comDataRetorno(LocalDate dataRetorno) {
        emprestimo.setDataPrevista(dataRetorno);
        return this;
    }

    public EmprestimoBuilder emAtraso() {
        emprestimo.setDataEmprestimo(LocalDate.now().minusDays(8) );
        emprestimo.setDataPrevista(LocalDate.now().minusDays(1) );
        return this;
    }


    public EmprestimoBuilder comValor(BigDecimal valor) {
        emprestimo.setValorTotal(valor);
        return this;
    }

    public EmprestimoBuilder jaFinalizada() {
        emprestimo.setDataEmprestimo(LocalDate.now().minusDays(7) );
        emprestimo.setDataPrevista(LocalDate.now().minusDays(6) );
        emprestimo.setDataDevolucao(LocalDate.now().minusDays(6) );
        emprestimo.setValorTotal(new BigDecimal(3.75));

        return this;
    }


    public Emprestimo constroi() {
        return emprestimo;
    }
}
