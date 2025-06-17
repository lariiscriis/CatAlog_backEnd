package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.Estante.TipoRelacao;
import com.example.CatALog.domain.user.Estante;
import com.example.CatALog.service.EstanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estante")
@CrossOrigin(origins = "*")
public class EstanteController {

    private final EstanteService estanteService;

    public EstanteController(EstanteService estanteService) {
        this.estanteService = estanteService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Estante> adicionarLivro(@RequestParam String idUsuario,
                                                  @RequestParam String idLivro,
                                                  @RequestParam TipoRelacao tipoRelacao) {
        Estante e = estanteService.adicionar(idUsuario, idLivro, tipoRelacao);
        return ResponseEntity.ok(e);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<Estante>> listarTodos(@PathVariable String idUsuario) {
        return ResponseEntity.ok(estanteService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/{idUsuario}/{tipo}")
    public ResponseEntity<List<Estante>> listarPorTipo(@PathVariable String idUsuario,
                                                       @PathVariable TipoRelacao tipo) {
        return ResponseEntity.ok(estanteService.listarPorTipo(idUsuario, tipo));
    }

    @DeleteMapping
    public ResponseEntity<String> remover(@RequestParam String idUsuario,
                                          @RequestParam String idLivro,
                                          @RequestParam TipoRelacao tipoRelacao) {
        estanteService.remover(idUsuario, idLivro, tipoRelacao);
        return ResponseEntity.ok("Removido com sucesso.");
    }

    @PutMapping("/trocarEstante")
    public ResponseEntity<Estante> trocarTipoRelacao(@RequestParam String idUsuario,
                                                     @RequestParam String idLivro,
                                                     @RequestParam TipoRelacao tipoAtual,
                                                     @RequestParam TipoRelacao tipoNovo) {
        Estante atualizado = estanteService.atualizarTipoRelacao(idUsuario, idLivro, tipoAtual, tipoNovo);
        return ResponseEntity.ok(atualizado);
    }

}
