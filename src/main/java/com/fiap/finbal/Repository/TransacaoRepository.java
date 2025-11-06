package com.fiap.finbal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fiap.finbal.model.Transacao;
import com.fiap.finbal.model.Categoria;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByCategoria(Categoria categoria);
}
