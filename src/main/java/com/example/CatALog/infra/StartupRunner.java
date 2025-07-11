package com.example.CatALog.infra;

import com.example.CatALog.repositories.BookRepository;
import com.example.CatALog.service.BookService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

    private final BookRepository bookRepository;
    private final BookService bookService;

    public StartupRunner(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (bookRepository.count() < 10) {
            System.out.println("Tabela de livros está vazia. Populando livros...");

            bookService.populatePorGenero();
        } else {
            System.out.println("Tabela de livros já possui registros.");
        }
    }
}
