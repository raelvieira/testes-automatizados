package br.com.israelvieira.modelo;

import java.math.BigDecimal;

import br.com.israelvieira.modelo.Emprestimo;

public interface TabelaDePreco {
	public BigDecimal calculaValor(long diasAtrasado, BigDecimal valorDoAluguel);
}
