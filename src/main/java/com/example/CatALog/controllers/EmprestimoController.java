package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.Emprestimo;
import com.example.CatALog.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimo")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
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
