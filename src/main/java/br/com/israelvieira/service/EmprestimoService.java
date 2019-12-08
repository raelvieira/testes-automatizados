package br.com.israelvieira.service;

import br.com.israelvieira.modelo.Emprestimo;
import br.com.israelvieira.modelo.Livro;
import br.com.israelvieira.modelo.Usuario;

import javax.naming.LimitExceededException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoService {

    private List<Emprestimo> listaFicticiaParaSimularBancoDeDados = new ArrayList<Emprestimo>();

    public Emprestimo emprestarLivro(Usuario usuario, Livro livro) throws LimitExceededException {
        if(livro.getEmprestado() || livro.getReservado())
            throw new RuntimeException("Livro indisponível");
        if(consultarEmprestimosPorUsuario(usuario).size() >= 2)
            throw  new LimitExceededException("O usuário excedeu o limite de empréstimos!");

        Emprestimo emprestimo = new Emprestimo(usuario, livro);
        livro.addHistoricoEmprestimo(emprestimo);
        livro.setEmprestado(true);

        return emprestimo;
    }

    public BigDecimal devolverLivro(Livro livro) {
        return livro.realizarDevolucao();
    }

    public List<Emprestimo> consultarEmprestimosPorUsuario(Usuario usuario) {
        List<Emprestimo> emprestimosUsuario = new ArrayList<Emprestimo>();

        this.listaFicticiaParaSimularBancoDeDados.forEach((emprestimo) -> {
            if(emprestimo.getUsuario().equals(usuario))
                emprestimosUsuario.add(emprestimo);
        });

        return emprestimosUsuario;
    }

    public void inicializaListaParaUmEmprestimo(Livro livro) {
        Emprestimo emprestimo1 = new Emprestimo(new Usuario("Fulado de tal", "M40E"), livro);
        this.listaFicticiaParaSimularBancoDeDados.add(emprestimo1);
    }

    public void inicializaListaParaDoisEmprestimos(Livro livro) {
        Emprestimo emprestimo1 = new Emprestimo(new Usuario("Fulado de tal", "M40E"), livro);
        Emprestimo emprestimo2 = new Emprestimo(new Usuario("Fulado de tal", "M40E"), livro);
        this.listaFicticiaParaSimularBancoDeDados.add(emprestimo1);
        this.listaFicticiaParaSimularBancoDeDados.add(emprestimo2);
    }
}
