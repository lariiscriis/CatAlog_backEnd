package com.example.CatALog.service;

import com.example.CatALog.domain.user.Notificacao;
import com.example.CatALog.repositories.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public void notificar(String idUser, String idLivro, String texto, LocalDateTime dataDevolucao) {
        Notificacao notificacao = new Notificacao();
        notificacao.setId(idUser);
        notificacao.setIdLivro(idLivro);
        notificacao.setTexto(texto);
        notificacao.setDataDevolucao(dataDevolucao);
        notificacao.setDataNotificacao(LocalDateTime.now());

        notificacaoRepository.save(notificacao);
    }
}

