package com.example.CatALog.controllers;

import com.example.CatALog.domain.user.Livro;
import com.example.CatALog.repositories.BookRepository;
import com.example.CatALog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.CatALog.dto.LivroDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CatALog.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Livro> listarTodos() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> detalhesLivro(@PathVariable String id) {
        return bookRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/populate")
    public String populatePorGenero() {
        try {
            var livros = bookService.populatePorGenero();
            return livros.size() + " livros salvos com sucesso por gênero.";
        } catch (Exception e) {
            return "Erro ao popular livros: " + e.getMessage();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionaLivro(@RequestBody Livro livro, @RequestParam String email) {
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário não encontrado.");
        }

        var user = userOptional.get();

        if (!user.getEmail().equals("admin@gmail.com")) {
            return ResponseEntity.status(403).body("Acesso negado. Apenas administradores podem adicionar livros.");
        }

        Livro livroSalvo = bookRepository.save(livro);
        return ResponseEntity.ok(livroSalvo);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizaLivro(
        @PathVariable String id,
        @RequestBody Livro livroAtualizado,
        @RequestParam String email) {
        var userOptional = userRepository.findByEmail(email);
        var user = userOptional.get();
        if (!user.getEmail().equals("admin@gmail.com")) {
            return ResponseEntity.status(403).body("Acesso negado. Apenas administradores podem adicionar livros.");
        }

        return bookRepository.findById(id).map(livro -> {
            livro.setTitulo(livroAtualizado.getTitulo());
            livro.setEditora(livroAtualizado.getEditora());
            livro.setAutores(livroAtualizado.getAutores());
            livro.setDescricao(livroAtualizado.getDescricao());
            livro.setCategoria(livroAtualizado.getCategoria());
            livro.setCapa(livroAtualizado.getCapa());
            livro.setData_publicacao(livroAtualizado.getData_publicacao());
            livro.setNumeroPaginas(livroAtualizado.getNumeroPaginas());
            livro.setQtdeLivro(livroAtualizado.getQtdeLivro());
            livro.setDisponibilidade(livroAtualizado.getDisponibilidade());

            return ResponseEntity.ok(bookRepository.save(livro));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletaLivro(@PathVariable String id,@RequestParam String email) {
        var userOptional = userRepository.findByEmail(email);
        var user = userOptional.get();
        if (!user.getEmail().equals("admin@gmail.com")) {
            return ResponseEntity.status(403).body("Acesso negado. Apenas administradores podem adicionar livros.");
        }

        return bookRepository.findById(id).map(livro -> {
            bookRepository.delete(livro);
            return ResponseEntity.ok("Livro deletado");
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/qtdeDisponiveis")
    public ResponseEntity<Integer> contarDisponiveis() {
        return ResponseEntity.ok(bookService.contarLivrosDisponiveis());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Livro>> listarLivrosDisponiveis() {
        return ResponseEntity.ok(bookService.listarLivrosDisponiveis());
    }


    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Livro>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(bookService.buscarPorTitulo(titulo));
    }

    @GetMapping("/buscar/autor")
    public ResponseEntity<List<Livro>> buscarPorAutor(@RequestParam String autores) {
        return ResponseEntity.ok(bookService.buscarPorAutor(autores));
    }

    @GetMapping("/buscar/autorOuTitulo")
    public ResponseEntity<List<Livro>> buscarLivrosPorAutorOuTitulo(@RequestParam String termo) {
        return ResponseEntity.ok(bookService.buscarLivrosPorAutorOuTitulo(termo));
    }

    @GetMapping("/ordenados/autor")
    public ResponseEntity<List<Livro>> listarPorAutor() {
        return ResponseEntity.ok(bookService.listarPorAutor());
    }
}
