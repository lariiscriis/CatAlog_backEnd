package com.example.CatALog.repositories;

import com.example.CatALog.domain.user.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findById(String idUsuario);
}
