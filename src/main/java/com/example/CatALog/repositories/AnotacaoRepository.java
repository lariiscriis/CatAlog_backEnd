package com.example.CatALog.repositories;

import com.example.CatALog.domain.user.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {
    List<Anotacao> findById(String idUsuario);
    List<Anotacao> findByIdAndIdLivro(String idUsuario, String idLivro);
    Optional<Anotacao> findByIdAndIdLivroAndPagina(String idUsuario, String idLivro, int pagina);
}
