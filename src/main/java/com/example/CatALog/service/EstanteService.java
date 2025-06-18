package com.example.CatALog.service;

import com.example.CatALog.domain.user.Estante.TipoRelacao;
import com.example.CatALog.domain.user.Estante;
import com.example.CatALog.domain.user.Livro;
import com.example.CatALog.repositories.BookRepository;
import com.example.CatALog.repositories.EstanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EstanteService {

    private final EstanteRepository estanteRepository;
    private BookRepository bookRepository;

    public EstanteService(EstanteRepository estanteRepository, BookRepository bookRepository) {
        this.estanteRepository = estanteRepository;
        this.bookRepository = bookRepository;
    }

    public Estante adicionar(String idUsuario, String idLivro, TipoRelacao tipo) {
        Estante estante = new Estante();
        Livro livro = bookRepository.findById(idLivro)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        estante.setId(idUsuario);
        estante.setLivro(livro);
        estante.setTipoRelacao(tipo);
        estante.setDataInteracao(LocalDateTime.now());

        return estanteRepository.save(estante);
    }

    public List<Estante> listarPorUsuario(String idUsuario) {
        return estanteRepository.findById(idUsuario);
    }

    public List<Estante> listarPorTipo(String idUsuario, TipoRelacao tipo) {
        return estanteRepository.findByIdAndTipoRelacao(idUsuario, tipo);
    }

    public void remover(String idUsuario, String idLivro, TipoRelacao tipo) {
        estanteRepository.findByIdAndLivro_IdLivroAndTipoRelacao(idUsuario, idLivro, tipo)
            .ifPresent(estanteRepository::delete);
    }

    @Transactional
    public Estante atualizarTipoRelacao(String idUsuario, String idLivro, TipoRelacao tipoAtual, TipoRelacao tipoNovo) {
        Estante estante = estanteRepository.findByIdAndLivro_IdLivroAndTipoRelacao(idUsuario, idLivro, tipoAtual)
            .orElseThrow(() -> new RuntimeException("Relacionamento não encontrado"));

        estante.setTipoRelacao(tipoNovo);
        estante.setDataInteracao(LocalDateTime.now());

        return estanteRepository.save(estante);
    }

}
