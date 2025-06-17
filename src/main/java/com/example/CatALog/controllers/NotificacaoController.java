package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.Notificacao;
import com.example.CatALog.repositories.NotificacaoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@CrossOrigin(origins = "*")
public class NotificacaoController {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoController(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    @GetMapping("/{idUsuario}")
    public List<Notificacao> listar(@PathVariable String idUsuario) {
        return notificacaoRepository.findById(idUsuario);
    }
}

