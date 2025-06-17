package com.example.CatALog.service;

import com.example.CatALog.domain.user.Estante.TipoRelacao;
import com.example.CatALog.domain.user.Estante;
import com.example.CatALog.repositories.EstanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EstanteService {

    private final EstanteRepository estanteRepository;

    public EstanteService(EstanteRepository estanteRepository) {
        this.estanteRepository = estanteRepository;
    }

    public Estante adicionar(String idUsuario, String idLivro, TipoRelacao tipo) {
        Estante estante = new Estante();
        estante.setId(idUsuario);
        estante.setIdLivro(idLivro);
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
        estanteRepository.findByIdAndIdLivroAndTipoRelacao(idUsuario, idLivro, tipo)
            .ifPresent(estanteRepository::delete);
    }

    @Transactional
    public Estante atualizarTipoRelacao(String idUsuario, String idLivro, TipoRelacao tipoAtual, TipoRelacao tipoNovo) {
        Estante estante = estanteRepository.findByIdAndIdLivroAndTipoRelacao(idUsuario, idLivro, tipoAtual)
            .orElseThrow(() -> new RuntimeException("Relacionamento não encontrado"));

        estante.setTipoRelacao(tipoNovo);
        estante.setDataInteracao(LocalDateTime.now());

        return estanteRepository.save(estante);
    }

}
