package com.example.CatALog.service;

import com.example.CatALog.domain.user.Anotacao;
import com.example.CatALog.repositories.AnotacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnotacaoService {

    private final AnotacaoRepository anotacaoRepository;

    public AnotacaoService(AnotacaoRepository anotacaoRepository) {
        this.anotacaoRepository = anotacaoRepository;
    }

    public Anotacao salvar(Anotacao anotacao) {
        anotacao.setDataNota(LocalDateTime.now());
        return anotacaoRepository.save(anotacao);
    }

    public List<Anotacao> listarPorUsuario(String idUsuario) {
        return anotacaoRepository.findById(idUsuario);
    }

    public List<Anotacao> listarPorLivro(String idUsuario, String idLivro) {
        return anotacaoRepository.findByIdAndIdLivro(idUsuario, idLivro);
    }

    public Anotacao atualizarNota(Long idAnotacao, Anotacao novaNota) {
        return anotacaoRepository.findById(idAnotacao).map(anotacao -> {
            anotacao.setNota(novaNota.getNota());
            anotacao.setAvaliacao(novaNota.getAvaliacao());
            anotacao.setPagina(novaNota.getPagina());
            anotacao.setDataNota(LocalDateTime.now());
            return anotacaoRepository.save(anotacao);
        }).orElseThrow(() -> new RuntimeException("Anotação não encontrada"));
    }

    public void deletar(Long idAnotacao) {
        anotacaoRepository.deleteById(idAnotacao);
    }
}
