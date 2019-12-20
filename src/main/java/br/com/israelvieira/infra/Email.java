package br.com.israelvieira.infra;

import br.com.israelvieira.modelo.Emprestimo;

public interface Email {
    void envia(Emprestimo emprestimo);
}
