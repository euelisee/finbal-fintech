package com.fiap.finbal.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fiap.finbal.DTO.MensagemResponseDTO;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<MensagemResponseDTO> handleUsuarioNaoEncontrado(UsuarioException ex) {
        MensagemResponseDTO resposta = new MensagemResponseDTO(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemResponseDTO> handleGenericException(Exception ex) {
        MensagemResponseDTO resposta = new MensagemResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}
