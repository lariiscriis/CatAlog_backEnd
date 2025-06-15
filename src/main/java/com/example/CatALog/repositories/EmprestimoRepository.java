package com.example.CatALog.repositories;

import com.example.CatALog.domain.user.Emprestimo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    Optional<Emprestimo> findById(Long idEmprestimo);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = """
    UPDATE Emprestimo
    SET data_prevista_devolucao = DATE_ADD(data_prevista_devolucao, INTERVAL 7 DAY),
        renovacoes = renovacoes + 1
    WHERE id_emprestimo = ?1 AND renovacoes < 2 AND estado = 'EM_ANDAMENTO'
    """, nativeQuery = true)
    int renovarEmprestimo(Long idEmprestimo);

}
