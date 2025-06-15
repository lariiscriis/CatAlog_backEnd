package com.example.CatALog.service;
import com.example.CatALog.domain.user.Emprestimo;
import com.example.CatALog.domain.user.Livro;
import com.example.CatALog.repositories.BookRepository;
import com.example.CatALog.repositories.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.CatALog.domain.user.Emprestimo.EstadoEmprestimo.EM_ANDAMENTO;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final BookRepository bookRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, BookRepository bookRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Emprestimo realizarEmprestimo(Emprestimo emprestimo) {
        Livro livro = bookRepository.findById(emprestimo.getIdLivro())
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livro.realizarEmprestimo();
        bookRepository.save(livro);

        // Se dataEmprestimo vier null, usa agora
        if (emprestimo.getDataEmprestimo() == null) {
            emprestimo.setDataEmprestimo(LocalDateTime.now());
        }
        // Se dataPrevistaDevolucao vier null, calcula 7 dias após dataEmprestimo
        if (emprestimo.getDataPrevistaDevolucao() == null) {
            emprestimo.setDataPrevistaDevolucao(emprestimo.getDataEmprestimo().plusDays(7));
        }

        emprestimo.setEstado(Emprestimo.EstadoEmprestimo.EM_ANDAMENTO);
        emprestimo.setRenovacoes(0);
        emprestimo.setMulta(BigDecimal.ZERO);

        return emprestimoRepository.save(emprestimo);
    }


    @Transactional
    public void renovarEmprestimo(Long idEmprestimo) {
        int atualizados = emprestimoRepository.renovarEmprestimo(idEmprestimo);
        System.out.println("Total de registros atualizados: " + atualizados);
        if (atualizados == 0) {
            throw new RuntimeException("Não foi possível renovar: empréstimo não encontrado, já renovado 2 vezes ou não está em andamento.");
        }
    }



    @Transactional
    public Emprestimo devolverLivro(Long idEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.findById(idEmprestimo)
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        Livro livro = bookRepository.findById(emprestimo.getIdLivro())
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livro.devolverLivro();
        bookRepository.save(livro);

        LocalDateTime agora = LocalDateTime.now();
        emprestimo.setDataDevolucao(agora);

        if (agora.isAfter(emprestimo.getDataPrevistaDevolucao())) {
            long diasAtraso = java.time.Duration.between(emprestimo.getDataPrevistaDevolucao(), agora).toDays();
            emprestimo.setMulta(BigDecimal.valueOf(diasAtraso * 5));
            emprestimo.setEstado(Emprestimo.EstadoEmprestimo.ATRASADO);
        } else {
            emprestimo.setMulta(BigDecimal.ZERO);
            emprestimo.setEstado(Emprestimo.EstadoEmprestimo.DEVOLVIDO);
        }

        return emprestimoRepository.save(emprestimo);
    }

}
