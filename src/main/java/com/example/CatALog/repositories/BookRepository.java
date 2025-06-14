package com.example.CatALog.repositories;

import com.example.CatALog.domain.user.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Livro, String> {

    Optional<Livro> findByTitulo(String titulo);

}
