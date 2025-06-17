package com.example.CatALog.domain.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Anotacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Anotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnotacao;

    @Column(nullable = false)
    private String id; // user

    @Column(nullable = false)
    private String idLivro; // livro

    private int pagina;

    private int avaliacao;

    private String nota ;

    private LocalDateTime dataNota;
}
