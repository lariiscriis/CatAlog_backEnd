package com.example.CatALog.service;

import com.example.CatALog.domain.user.Livro;
import com.example.CatALog.dto.LivroDTO;
import com.example.CatALog.repositories.BookRepository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final Gson gson = new Gson();

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public int contarLivrosDisponiveis() {
        return bookRepository.countLivrosDisponiveis();
    }

    public List<Livro> listarLivrosDisponiveis() {
        return bookRepository.findByDisponibilidadeTrue();
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return bookRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Livro> buscarPorAutor(String autores) {
        return bookRepository.findByAutoresContainingIgnoreCase(autores);
    }

    public List<Livro> listarPorAutor() {
        return bookRepository.listarTodosPorAutor();
    }

    public List<Livro> buscarLivrosPorAutorOuTitulo(String termo) {
        return bookRepository.findByTituloContainingIgnoreCaseOrAutoresContainingIgnoreCase(termo, termo);
    }

    public List<Livro> populatePorGenero() throws IOException, InterruptedException {
        List<String> generos = List.of("fantasy", "romance", "science fiction", "history", "biography");
        List<Livro> todos = new ArrayList<>();

        for (String genero : generos) {
            System.out.println("Importando: " + genero);
            todos.addAll(populateBooksBySubject(genero));
        }
        return todos;
    }

    public List<Livro> populateBooksBySubject(String genero) throws IOException, InterruptedException {
        JsonObject resposta = ConsomeAPI.buscarLivrosPorGenero(genero);
        JsonArray items = resposta.getAsJsonArray("items");

        List<Livro> livros = new ArrayList<>();

        if (items != null) {
            for (JsonElement elemento : items) {
                LivroDTO dto = gson.fromJson(elemento, LivroDTO.class);
                Livro livro = new Livro();

                livro.setTitulo(dto.volumeInfo.title);
                livro.setAutores(dto.volumeInfo.authors != null ? String.join(", ", dto.volumeInfo.authors) : "Desconhecido");
                livro.setEditora(dto.volumeInfo.publisher);
                livro.setData_publicacao(dto.volumeInfo.publishedDate);
                livro.setDescricao(dto.volumeInfo.description);
                livro.setCapa(dto.volumeInfo.imageLinks != null ? dto.volumeInfo.imageLinks.thumbnail : null);
                livro.setQtdeLivro(1);
                livro.setDisponibilidade(true);
                livro.setNumeroPaginas(dto.volumeInfo.pageCount);
                livro.setCategoria(genero);

                if (dto.volumeInfo.industryIdentifiers != null) {
                    dto.volumeInfo.industryIdentifiers.stream()
                        .filter(id -> id.type.equalsIgnoreCase("ISBN_13"))
                        .findFirst()
                        .ifPresent(isbn -> livro.setIsbn(isbn.identifier));
                }

                if (bookRepository.findByTitulo(livro.getTitulo()).isEmpty()) {
                    livros.add(bookRepository.save(livro));
                }
            }
        }

        return livros;
    }

    public List<LivroDTO> listarTodosLivros() {
        List<Livro> livros = bookRepository.findAll();
        List<LivroDTO> dtos = new ArrayList<>();

        for (Livro livro : livros) {
            LivroDTO dto = new LivroDTO();
            dto.id = String.valueOf(livro.getIdLivro());

            LivroDTO.VolumeInfo volumeInfo = new LivroDTO.VolumeInfo();
            volumeInfo.title = livro.getTitulo();
            volumeInfo.authors = List.of(livro.getAutores().split(",\\s*"));
            volumeInfo.description = livro.getDescricao();

            LivroDTO.ImageLinks imageLinks = new LivroDTO.ImageLinks();
            imageLinks.thumbnail = livro.getCapa();
            volumeInfo.imageLinks = imageLinks;

            dto.volumeInfo = volumeInfo;

            dtos.add(dto);
        }

        return dtos;
    }

}
