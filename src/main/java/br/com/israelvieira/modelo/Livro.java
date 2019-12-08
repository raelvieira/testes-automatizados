package br.com.israelvieira.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String autor;
    private String titulo;
    private Boolean isEmprestado;
    private Boolean isReservado;

    @Column(name = "valor_aluguel")
    private BigDecimal valorAluguel;

    @OneToMany(mappedBy = "livroLocado")
    private List<Emprestimo> historico = new ArrayList<Emprestimo>();

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Boolean getEmprestado() {
        return isEmprestado.booleanValue();
    }

    public void setEmprestado(Boolean emprestado) {
        isEmprestado = emprestado;
    }

    public Boolean getReservado() {
        return isReservado.booleanValue();
    }

    public void setReservado(Boolean reservado) {
        isReservado = reservado;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Emprestimo> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

    public void addHistoricoEmprestimo(Emprestimo emprestimo) {
        historico.add(emprestimo);
    }

    private Emprestimo retornaEmprestimoAtivo() {
        return historico.get(historico.size() - 1);
    }

    public BigDecimal realizarDevolucao() {
        this.isEmprestado = false;
        Emprestimo emprestimoAtivo = retornaEmprestimoAtivo();
        return emprestimoAtivo.devolucao(this.valorAluguel);
    }
}
