package com.example.CatALog.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notificacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacao;

    @Column(nullable = false)
    private String id; // user

    @Column(nullable = false)
    private String idLivro; // livro

    private String texto ;

    private LocalDateTime dataDevolucao;

    private LocalDateTime dataNotificacao;
}
