package com.example.CatALog.domain.user;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmprestimo;

    @Column(nullable = false)
    private String id; // user

    @Column(nullable = false)
    private String idLivro; // livro

    private LocalDateTime dataEmprestimo;

    private LocalDateTime dataPrevistaDevolucao;

    private LocalDateTime dataDevolucao;

    @Enumerated(EnumType.STRING)
    private EstadoEmprestimo estado;

    private int renovacoes;

    @Column(precision = 10, scale = 2)
    private BigDecimal multa;

    @Transient // se não for mapeado no banco
    private Livro livro;

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    // Getters e Setters

    public Long getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Long idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDateTime getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDateTime dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public EstadoEmprestimo getEstado() {
        return estado;
    }

    public void setEstado(EstadoEmprestimo estado) {
        this.estado = estado;
    }

    public int getRenovacoes() {
        return renovacoes;
    }

    public void setRenovacoes(int renovacoes) {
        this.renovacoes = renovacoes;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public enum EstadoEmprestimo {
        EM_ANDAMENTO,
        ATRASADO,
        DEVOLVIDO
    }



    @Override
    public String toString() {
        return "Emprestimo{" +
            "idEmprestimo=" + idEmprestimo +
            ", id='" + id + '\'' +
            ", idLivro='" + idLivro + '\'' +
            ", dataEmprestimo=" + dataEmprestimo +
            ", dataPrevistaDevolucao=" + dataPrevistaDevolucao +
            ", dataDevolucao=" + dataDevolucao +
            ", estado=" + estado +
            ", renovacoes=" + renovacoes +
            ", multa=" + multa +
            '}';
    }
}
