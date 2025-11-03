package com.fiap.finbal.controller;

import com.fiap.finbal.DTO.TransacaoRequestDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fiap.finbal.model.Transacao;
import com.fiap.finbal.Service.TransacaoService;
import com.fiap.finbal.dto.MensagemResponseDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {this.service = service;}

    @GetMapping("/transacoes")
    public ResponseEntity<List<Transacao>> listarTransacoes() {
        List<Transacao> transacoes = service.ListarTransacoes();
        return ResponseEntity.status(HttpStatus.OK).body(transacoes);
    }

    @PostMapping("/transacoes")
    public ResponseEntity<MensagemResponseDTO> criarTransacao(@RequestBody TransacaoRequestDTO transacaoRequestDTO) {
        try {
            Transacao novaTransacao = service.registrarTransacao(transacaoRequestDTO);
            MensagemResponseDTO resposta = new MensagemResponseDTO(
                    HttpStatus.CREATED,
                    "Transação cadastrada com sucesso!",
                    novaTransacao);

            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        } catch (RuntimeException e) {
            MensagemResponseDTO erro = new MensagemResponseDTO(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }


}
