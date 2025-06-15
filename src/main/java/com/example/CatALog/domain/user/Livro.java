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
    private String id_livro;

    private String isbn;

    private String titulo;

    private String editora;

    private String data_publicacao;

    @Column(columnDefinition = "TEXT")
    private String autores;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String capa;

    private int qtdeLivro;

    private Boolean disponibilidade;

    private int numeroPaginas;

    @Column(length = 255)
    private String categoria;

}

