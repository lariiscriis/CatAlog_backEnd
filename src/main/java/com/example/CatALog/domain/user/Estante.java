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

    @ManyToOne
    @JoinColumn(name = "id_livro", foreignKey = @ForeignKey(name = "FK_ESTANTE_LIVRO", foreignKeyDefinition = "FOREIGN KEY (id_livro) REFERENCES livro(id_livro) ON DELETE CASCADE"))
    private Livro livro;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoRelacao tipoRelacao;

    private LocalDateTime dataInteracao;

    public void setId(String id) {
        this.id = id;
    }

    public void setIdLivro(Livro idLivro) {
        this.livro = idLivro;
    }

    public void setTipoRelacao(TipoRelacao tipoRelacao) {
        this.tipoRelacao = tipoRelacao;
    }

    public void setDataInteracao(LocalDateTime dataInteracao) {
        this.dataInteracao = dataInteracao;
    }



    public enum TipoRelacao {
        favorito,
        emprestado,
        desejado
    }
}

