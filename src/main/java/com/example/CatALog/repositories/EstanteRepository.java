package com.example.CatALog.repositories;

import com.example.CatALog.domain.user.Estante;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.CatALog.domain.user.Estante.TipoRelacao;


import java.util.List;
import java.util.Optional;

public interface EstanteRepository extends JpaRepository<Estante, Long> {
    List<Estante> findById(String id);
    List<Estante> findByIdAndTipoRelacao(String id, TipoRelacao tipoRelacao);
    Optional<Estante> findByIdAndIdLivroAndTipoRelacao(String id, String idLivro, TipoRelacao tipoRelacao);
}
