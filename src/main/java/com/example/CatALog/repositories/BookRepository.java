package com.example.CatALog.repositories;

import com.example.CatALog.domain.user.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Livro, String> {

    Optional<Livro> findByTitulo(String titulo);

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByAutoresContainingIgnoreCase(String autores);

    @Query(value = "SELECT COUNT(*) FROM Livro WHERE disponibilidade = true", nativeQuery = true)
    int countLivrosDisponiveis();

    @Query("SELECT l FROM Livro l ORDER BY l.autores ASC")
    List<Livro> listarTodosPorAutor();
}
