package br.com.israelvieira.modelo;

import java.math.BigDecimal;

public class TabelaDePrecoMulta implements TabelaDePreco {

	@Override
	public BigDecimal calculaValor(long diasAtrasado, BigDecimal valorDoAluguel) {
	 	BigDecimal valorMulta = BigDecimal.ZERO;
		 
		 for(int i = 0; i < diasAtrasado; i++) {
			 valorMulta = valorMulta.add(new BigDecimal(0.4));
		 }
		 
		 if(valorMulta.compareTo(valorDoAluguel) == 1) {
			 valorMulta = valorDoAluguel;
		 }
		 
		 return new BigDecimal(5).add(valorMulta);
	}

}
