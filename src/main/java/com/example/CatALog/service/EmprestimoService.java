package com.example.CatALog.service;
import com.example.CatALog.domain.user.Emprestimo;
import com.example.CatALog.domain.user.Estante;
import com.example.CatALog.domain.user.Livro;
import com.example.CatALog.repositories.BookRepository;
import com.example.CatALog.repositories.EmprestimoRepository;
import com.example.CatALog.repositories.EstanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.example.CatALog.domain.user.Emprestimo.EstadoEmprestimo.EM_ANDAMENTO;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final BookRepository bookRepository;
    private final NotificacaoService notificacaoService;
    private final EstanteRepository estanteRepository;
    private final EstanteService estanteService;

    public EmprestimoService(EstanteService estanteService, EmprestimoRepository emprestimoRepository, BookRepository bookRepository,NotificacaoService notificacaoService,EstanteRepository estanteRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.bookRepository = bookRepository;
        this.notificacaoService = notificacaoService;
        this.estanteRepository = estanteRepository;
        this.estanteService = estanteService;
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public List<Emprestimo> listarPorUsuario(String idUsuario) {
        return emprestimoRepository.findById(idUsuario);
    }

    public List<Emprestimo> listarVencidos() {
        return emprestimoRepository.findEmprestimosVencidos(LocalDateTime.now());
    }

    public List<Emprestimo> listarEmprestimosAtivosPorUsuario(String idUsuario) {
        List<Emprestimo> emprestimos = emprestimoRepository.findByIdAndDataDevolucaoIsNull(idUsuario);
        for (Emprestimo e : emprestimos) {
            Livro livro = bookRepository.findById(e.getIdLivro()).orElse(null);
            e.setLivro(livro); // isso pressupõe que Emprestimo tem o campo Livro livro;
        }
        return emprestimos;
    }


    @Transactional
    public Emprestimo realizarEmprestimo(Emprestimo emprestimo) {
        Livro livro = bookRepository.findById(emprestimo.getIdLivro())
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livro.realizarEmprestimo();
        bookRepository.save(livro);

        if (emprestimo.getDataEmprestimo() == null) {
            emprestimo.setDataEmprestimo(LocalDateTime.now());
        }
        if (emprestimo.getDataPrevistaDevolucao() == null) {
            emprestimo.setDataPrevistaDevolucao(emprestimo.getDataEmprestimo().plusDays(7));
        }

        emprestimo.setEstado(Emprestimo.EstadoEmprestimo.EM_ANDAMENTO);
        emprestimo.setRenovacoes(0);
        emprestimo.setMulta(BigDecimal.ZERO);

        notificacaoService.notificar(
            emprestimo.getId(),
            emprestimo.getIdLivro(),
            "Você pegou emprestado o livro " + livro.getTitulo(),
            emprestimo.getDataPrevistaDevolucao()
        );

        estanteService.remover(
            emprestimo.getId(),
            emprestimo.getIdLivro(),
            Estante.TipoRelacao.desejado
        );

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

    @Scheduled(cron = "0 0 9 * * ?") // todos os dias às 9h
    public void verificarDevolucoesProximas() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        LocalDateTime agora = LocalDateTime.now();

        for (Emprestimo e : emprestimos) {
            if (e.getEstado() == EM_ANDAMENTO) {
                long dias = ChronoUnit.DAYS.between(agora, e.getDataPrevistaDevolucao());
                if (dias == 1) {
                    notificacaoService.notificar(
                        e.getId(),
                        e.getIdLivro(),
                        "A devolução do livro está prevista para amanhã" ,
                        e.getDataPrevistaDevolucao()

                    );
                }
            }
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

        String texto = "Você devolveu o livro " + livro.getTitulo();
        if (emprestimo.getMulta().compareTo(BigDecimal.ZERO) > 0) {
            texto += " com multa de R$" + emprestimo.getMulta();
        }
        notificacaoService.notificar(emprestimo.getId(), emprestimo.getIdLivro(), texto, emprestimo.getDataDevolucao());

        // Remove o livro da estante de emprestado após devolução
        estanteService.remover(
            emprestimo.getId(),
            emprestimo.getIdLivro(),
            Estante.TipoRelacao.emprestado
        );

        return emprestimoRepository.save(emprestimo);
    }


}
