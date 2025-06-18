package com.example.CatALog.repositories;

import com.example.CatALog.domain.user.Estante;
import com.example.CatALog.domain.user.Estante.TipoRelacao;
import com.example.CatALog.domain.user.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstanteRepository extends JpaRepository<Estante, Long> {
    // Buscar por ID do usuário (que é uma String)
    List<Estante> findById(String id);

    // Buscar por ID do usuário e tipo de relação
    List<Estante> findByIdAndTipoRelacao(String id, TipoRelacao tipoRelacao);

    // Buscar por ID do usuário, ID do livro e tipo de relação
    Optional<Estante> findByIdAndLivro_IdLivroAndTipoRelacao(String id, String livroId, TipoRelacao tipoRelacao);

}