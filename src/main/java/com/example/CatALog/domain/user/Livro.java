package com.example.CatALog.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Livro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_livro")
    private String idLivro;

    private String isbn;

    private String titulo;

    private String editora;

    private String data_publicacao;

    @Column(columnDefinition = "TEXT")
    private String autores;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String capa;

    private Integer qtdeLivro;

    private Boolean disponibilidade;

    private Integer numeroPaginas;

    @Column(length = 255)
    private String categoria;


    public String getIdLivro() {
        return idLivro;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEditora() {
        return editora;
    }

    public String getData_publicacao() {
        return data_publicacao;
    }

    public String getAutores() {
        return autores;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCapa() {
        return capa;
    }

    public Integer getQtdeLivro() {
        return qtdeLivro;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getCategoria() {
        return categoria;
    }
// Metodos da classe

    public void realizarEmprestimo() {
        if (qtdeLivro <= 0 || !disponibilidade) {
            throw new RuntimeException("Livro indisponível para empréstimo");
        }
        qtdeLivro--;
        if (qtdeLivro == 0) {
            disponibilidade = false;
        }
    }

    public void devolverLivro() {
        qtdeLivro++;
        if (qtdeLivro > 0) {
            disponibilidade = true;
        }
    }

    @Override
    public String toString() {
        return "Livro{" +
            "id_livro='" + idLivro + '\'' +
            ", isbn='" + isbn + '\'' +
            ", titulo='" + titulo + '\'' +
            ", editora='" + editora + '\'' +
            ", data_publicacao='" + data_publicacao + '\'' +
            ", autores='" + autores + '\'' +
            ", descricao='" + descricao + '\'' +
            ", capa='" + capa + '\'' +
            ", qtdeLivro=" + qtdeLivro +
            ", disponibilidade=" + disponibilidade +
            ", numeroPaginas=" + numeroPaginas +
            ", categoria='" + categoria + '\'' +
            '}';
    }
}

