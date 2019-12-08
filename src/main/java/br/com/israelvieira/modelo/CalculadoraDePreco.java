package br.com.israelvieira.modelo;

import java.math.BigDecimal;

public class CalculadoraDePreco {
	
	private TabelaDePreco tabela;
	
	public CalculadoraDePreco(TabelaDePreco tabela) {
		this.tabela = tabela;
	}
	
	public BigDecimal calcula(long diasAtrasado, BigDecimal valorDoAluguel) {
		return tabela.calculaValor(diasAtrasado, valorDoAluguel);
	}
}
