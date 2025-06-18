package com.example.CatALog.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Table(name = "Estante")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstante;

    @Column(nullable = false)
    private String id; // user

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_livro", referencedColumnName = "id_livro", nullable = false)
    private Livro livro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRelacao tipoRelacao;

    private LocalDateTime dataInteracao;

    public enum TipoRelacao {
        favorito,
        emprestado,
        desejado
    }
}

