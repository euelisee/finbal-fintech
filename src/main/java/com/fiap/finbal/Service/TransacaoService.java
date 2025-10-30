package com.fiap.finbal.service;


import com.fiap.finbal.Service.ContaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import com.fiap.finbal.repository.TransacaoRepository;
import com.fiap.finbal.model.Transacao;
import com.fiap.finbal.model.Categoria;
import com.fiap.finbal.Model.Conta;
import com.fiap.finbal.Repository.ContaRepository;



@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final ContaService contaService;


    @Autowired
    public TransacaoService(TransacaoRepository transacaoRepository,  ContaRepository contaRepository, ContaService contaService) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.contaService = contaService;
    }

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

    //ajustar a string RECEITA e DESPESA para um enum (evitar erro de escrita)
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
        Optional<Conta> contaOptional = contaRepository.findById(contaId);
        if (contaOptional.isEmpty()) {
            return "ERRO: Conta não encontrada";
        }
        Conta conta = contaOptional.get();

        conta.setSaldo(conta.getSaldo().add(transacao.getValor()));
        contaRepository.save(conta);
        transacao.setConta(conta);
        transacaoRepository.save(transacao);
        return "Receita adicionada com sucesso";
    }

    private String executarDespesa(Transacao transacao, Long contaId) {
        Optional<Conta> contaOptional = this.contaService.buscarContaPorId(contaId);
        if (contaOptional.isEmpty()) {
            return "ERRO: Conta não encontrada.";
        }
        Conta conta = contaOptional.get();

        if (conta.getSaldo().compareTo(transacao.getValor()) < 0) {
            return "Saldo insuficiente";
        }

        conta.setSaldo(conta.getSaldo().subtract(transacao.getValor()));
        contaRepository.save(conta);
        transacao.setConta(conta);
        transacaoRepository.save(transacao);
        return "Despesa adicionada com sucesso";
    }


}
