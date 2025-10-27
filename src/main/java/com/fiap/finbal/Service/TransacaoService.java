package com.fiap.finbal.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import com.fiap.finbal.repository.TransacaoRepository;
import com.fiap.finbal.model.Transacao;
import com.fiap.finbal.model.Categoria;
import com.fiap.finbal.model.Conta;
import com.fiap.finbal.repository.ContaRepository;



@Service
public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private ContaRepository contaRepository;

    public Transacao salvar(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public List<Transacao> buscarPorCategoria(String categoria) {

        Categoria categoriaEnum;
        try {
           categoriaEnum = Categoria.valueOf(categoria.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Erro ao buscar por categoria: " + categoria + ". A categoria não é válida ou é nula.");
            return null;
        }

        return transacaoRepository.findByCategoria(categoriaEnum);

    }

    public List<Transacao> ListarTransacoes() {return transacaoRepository.findAll();}
    public Optional<Transacao> buscarPorId(Long id) {return transacaoRepository.findById(id);}


    @Transactional
    public String registrarTransacao(Transacao transacao, Conta conta) {
        if (transacao.getTipo() == "RECEITA"){
            return executarReceita(transacao, conta.getId());
        } else if (transacao.getTipo() == "DESPESA"){
            return executarDespesa(transacao, conta.getId());
        } else {
            return "ERRO: Tipo de transação desconhecido.";
        }
    }
    private String executarReceita(Transacao transacao, Long contaId) {
        Conta conta = contaRepository.findById(contaId).get(); //
        conta.setSaldo(conta.getSaldo() + (transacao.getValor()));
        contaRepository.save(conta);
        transacao.setConta(conta);
        transacaoRepository.save(transacao);
        return "Receita adicionada com sucesso";
    }

    private String executarDespesa(Transacao transacao, Long contaId) {
        Conta conta = contaRepository.findById(contaId).get();

        if (conta.getSaldo().compareTo(transacao.getValor()) < 0){return "Saldo insuficiente";}

        conta.setSaldo(conta.getSaldo() - (transacao.getValor()));
        contaRepository.save(conta);
        transacaoRepository.save(transacao);
        return "Despesa adicionada com sucesso";
    }


}
