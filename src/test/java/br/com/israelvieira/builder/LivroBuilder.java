package br.com.israelvieira.builder;

import br.com.israelvieira.modelo.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LivroBuilder {
    private Livro livro;

    private LivroBuilder() { }

    public static LivroBuilder umLivro() {
        LivroBuilder builder = new LivroBuilder();

        builder.livro = new Livro();
        builder.livro.setAutor("Autor 1");
        builder.livro.setTitulo("Livro 1");
        builder.livro.setValorAluguel(new BigDecimal(3.5));
        builder.livro.setEmprestado(false);
        builder.livro.setReservado(false);

        return builder;
    }

    public LivroBuilder emprestado() {
        this.livro.addHistoricoEmprestimo(EmprestimoBuilder.umEmprestimo().constroi());

        this.livro.setEmprestado(true);
        return this;
    }

    public LivroBuilder emprestimoAtrasadoUmDia() {

        this.livro.addHistoricoEmprestimo(
            EmprestimoBuilder.umEmprestimo()
                    .comLivro(this.livro)
                    .comDataRetorno(LocalDate.now().minusDays(1)).constroi()
        );

        this.livro.setValorAluguel(new BigDecimal(2.75));
        this.livro.setEmprestado(true);

        return this;
    }

    public LivroBuilder emprestimoAtrasadoTrintaDias() {
        this.livro.addHistoricoEmprestimo(
            EmprestimoBuilder.umEmprestimo()
                    .comLivro(this.livro)
                    .comDataRetorno(LocalDate.now().minusDays(30)).constroi()
        );

        this.livro.setValorAluguel(new BigDecimal(2.75));
        this.livro.setEmprestado(true);

        return this;
    }

    public LivroBuilder reservado() {
        this.livro.setReservado(true);
        return this;
    }

    public LivroBuilder comPreco(BigDecimal precoLivro) {
        this.livro.setValorAluguel(precoLivro);
        return this;
    }

    public LivroBuilder comTitulo(String tituloLivro) {
        this.livro.setTitulo(tituloLivro);
        return this;
    }

    public Livro constroi() {
        return this.livro;
    }
}
