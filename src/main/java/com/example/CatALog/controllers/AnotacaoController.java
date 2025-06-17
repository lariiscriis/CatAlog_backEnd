package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.Anotacao;
import com.example.CatALog.service.AnotacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anotacoes")
@CrossOrigin(origins = "*")
public class AnotacaoController {

    private final AnotacaoService anotacaoService;

    public AnotacaoController(AnotacaoService anotacaoService) {
        this.anotacaoService = anotacaoService;
    }

    @PostMapping
    public ResponseEntity<Anotacao> criar(@RequestBody Anotacao anotacao) {
        return ResponseEntity.ok(anotacaoService.salvar(anotacao));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<Anotacao>> listarPorUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(anotacaoService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/{idUsuario}/livro/{idLivro}")
    public ResponseEntity<List<Anotacao>> listarPorLivro(@PathVariable String idUsuario, @PathVariable String idLivro) {
        return ResponseEntity.ok(anotacaoService.listarPorLivro(idUsuario, idLivro));
    }

    @PutMapping("/{idAnotacao}")
    public ResponseEntity<Anotacao> atualizar(@PathVariable Long idAnotacao, @RequestBody Anotacao novaNota) {
        return ResponseEntity.ok(anotacaoService.atualizarNota(idAnotacao, novaNota));
    }

    @DeleteMapping("/{idAnotacao}")
    public ResponseEntity<Void> deletar(@PathVariable Long idAnotacao) {
        anotacaoService.deletar(idAnotacao);
        return ResponseEntity.noContent().build();
    }
}
