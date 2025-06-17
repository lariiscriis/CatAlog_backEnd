package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.Emprestimo;
import com.example.CatALog.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimo")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodos() {
        return ResponseEntity.ok(emprestimoService.listarTodos());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<Emprestimo>> listarPorUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(emprestimoService.listarPorUsuario(idUsuario));
    }

    // 3. Empréstimos vencidos (atrasados)
    @GetMapping("/vencidos")
    public ResponseEntity<List<Emprestimo>> listarVencidos() {
        return ResponseEntity.ok(emprestimoService.listarVencidos());
    }
    @PostMapping
    public ResponseEntity<Emprestimo> realizarEmprestimo(@RequestBody Emprestimo emprestimo) {
        Emprestimo salvo = emprestimoService.realizarEmprestimo(emprestimo);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/renovar/{idEmprestimo}")
    public ResponseEntity<String> renovarEmprestimo(@PathVariable Long idEmprestimo) {
        emprestimoService.renovarEmprestimo(idEmprestimo);
        return ResponseEntity.ok("Renovação realizada com sucesso.");
    }

    @PutMapping("/devolver/{idEmprestimo}")
    public ResponseEntity<Emprestimo> devolverEmprestimo(@PathVariable Long idEmprestimo) {
        Emprestimo devolvido = emprestimoService.devolverLivro(idEmprestimo);
        return ResponseEntity.ok(devolvido);
    }
}
