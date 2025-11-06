package com.fiap.finbal.Service;


import com.fiap.finbal.DTO.TransacaoRequestDTO;
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
import com.fiap.finbal.Model.TipoTransacao;
import com.fiap.finbal.Repository.ContaRepository;


@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final ContaService contaService;


    @Autowired
    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository, ContaService contaService) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
        this.contaService = contaService;
    }

    public List<Transacao> buscarPorCategoria(String categoria) {

        Categoria categoriaEnum;
        try {
            categoriaEnum = Categoria.valueOf(categoria.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Erro ao buscar por categoria: " + categoria + ". A categoria não é válida ou é nula.");
            return java.util.Collections.emptyList();
        }

        return transacaoRepository.findByCategoria(categoriaEnum);

    }

    public List<Transacao> ListarTransacoes() {
        return transacaoRepository.findAll();
    }

    public Optional<Transacao> buscarTransacaoPorId(Long id) {
        return transacaoRepository.findById(id);
    }


    @Transactional
    public Transacao registrarTransacao(TransacaoRequestDTO dto) {
        if (dto.contaId() == null) {
            throw new IllegalArgumentException("ERRO: O 'contaId' não pode ser nulo.");
        }

        Conta conta = contaService.buscarContaPorId(dto.contaId())
                .orElseThrow(() -> new RuntimeException("ERRO: Conta com ID " + dto.contaId() + " não encontrada."));

        Transacao novaTransacao = new Transacao();
        novaTransacao.setTipo(dto.tipo());
        novaTransacao.setValor(dto.valor());
        novaTransacao.setCategoria(dto.categoria());
        novaTransacao.setData(dto.data());
        novaTransacao.setConta(conta);

        switch (novaTransacao.getTipo()) {
            case RECEITA:
                conta.setSaldo(conta.getSaldo().add(novaTransacao.getValor()));
                break;
            case DESPESA:
                if (conta.getSaldo().compareTo(novaTransacao.getValor()) < 0) {
                    throw new RuntimeException("Saldo insuficiente na conta " + conta.getId());
                }
                conta.setSaldo(conta.getSaldo().subtract(novaTransacao.getValor()));
                break;
            default:
                throw new RuntimeException("ERRO: Tipo de transação desconhecido.");
        }

        contaRepository.save(conta);
        return transacaoRepository.save(novaTransacao);
    }

    @Transactional
    public void deletarTransacao(Long id) {
        Transacao transacaoExistente = this.buscarTransacaoPorId(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada para deleção (ID: " + id + ")"));

        Conta conta = transacaoExistente.getConta();

        switch (transacaoExistente.getTipo()) {
            case RECEITA:
                conta.setSaldo(conta.getSaldo().subtract(transacaoExistente.getValor()));
                transacaoRepository.deleteById(id);
                break;
            case DESPESA:
                conta.setSaldo(conta.getSaldo().add(transacaoExistente.getValor()));
                transacaoRepository.deleteById(id);
                break;
            default:
                throw new RuntimeException("ERRO: Tipo de transação desconhecido.");
        }
        contaRepository.save(conta);
    }

    @Transactional
    public Transacao atualizarTransacao(Long id, TransacaoRequestDTO dto) {
        return transacaoRepository.findById(id)
                .map(transacaoExistente -> {
                    Conta conta = transacaoExistente.getConta();

                    switch (transacaoExistente.getTipo()) {
                        case RECEITA:
                            conta.setSaldo(conta.getSaldo().subtract(transacaoExistente.getValor()));
                            break;
                            case DESPESA:
                                conta.setSaldo(conta.getSaldo().add(transacaoExistente.getValor()));
                                break;
                    }

                    if (dto.tipo() != null) {
                        transacaoExistente.setTipo(dto.tipo());
                    }
                    if (dto.valor() != null) {
                        transacaoExistente.setValor(dto.valor());
                    }
                    if (dto.categoria() != null) {
                        transacaoExistente.setCategoria(dto.categoria());
                    }
                    if (dto.data() != null) {
                        transacaoExistente.setData(dto.data());
                    }

                    switch (transacaoExistente.getTipo()) {
                        case RECEITA:
                            conta.setSaldo(conta.getSaldo().add(transacaoExistente.getValor()));
                            break;
                        case DESPESA:
                            if (conta.getSaldo().compareTo(transacaoExistente.getValor()) < 0) {
                                throw new RuntimeException("Saldo insuficiente para atualizar esta despesa.");
                            }
                            conta.setSaldo(conta.getSaldo().subtract(transacaoExistente.getValor()));
                            break;
                    }

                    contaRepository.save(conta);
                    return transacaoRepository.save(transacaoExistente);
                })

                .orElseThrow(() -> new RuntimeException("Transação não encontrada para atualização (ID: " + id + ")"));
    }
}
