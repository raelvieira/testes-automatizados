package br.com.israelvieira.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Usuario usuario;

    @Column(name = "data_emprestimo")
    private LocalDate dataEmprestimo = LocalDate.now();

    @Column(name = "data_prevista")
    private LocalDate dataPrevista;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @ManyToOne
    @JoinColumn(name = "livro_fk")
    private Livro livroLocado;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @OneToMany
    @JoinColumn(name = "emprestimo_fk")
    private List<Pagamento> pagamento;

    public Emprestimo() {
    }

    public Emprestimo(Usuario usuario, Livro livroLocado) {
        this.usuario = usuario;
        this.dataPrevista = dataEmprestimo.plusDays(7);
        this.livroLocado = livroLocado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Livro getLivroLocado() {
        return livroLocado;
    }

    public void setLivroLocado(Livro livroLocado) {
        this.livroLocado = livroLocado;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Pagamento> getPagamento() {
        return pagamento;
    }

    public void setPagamento(List<Pagamento> pagamento) {
        this.pagamento = pagamento;
    }

    public long getDiasEmAtraso() {
        return ChronoUnit.DAYS.between(this.dataPrevista, this.dataDevolucao);
    }

	public BigDecimal devolucao(BigDecimal valorDoAluguel) {
		this.dataDevolucao = LocalDate.now();
		CalculadoraDePreco calculadora;

		if(this.getDiasEmAtraso() > 0)
			calculadora = new CalculadoraDePreco(new TabelaDePrecoMulta());
		else
			calculadora = new CalculadoraDePreco(new TabelaDePrecoPadrao());
		
		this.valorTotal = calculadora.calcula(this.getDiasEmAtraso(), valorDoAluguel);
		
		return this.valorTotal;
	}

}
