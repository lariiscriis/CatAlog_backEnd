package com.example.CatALog.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CatALog.service.BookService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
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
}
