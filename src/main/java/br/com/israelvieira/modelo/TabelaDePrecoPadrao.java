package br.com.israelvieira.modelo;

import java.math.BigDecimal;

public class TabelaDePrecoPadrao implements TabelaDePreco {

	@Override
	public BigDecimal calculaValor(long diasAtrasado, BigDecimal valorDoAluguel) {
		return new BigDecimal(5);
	}

}
