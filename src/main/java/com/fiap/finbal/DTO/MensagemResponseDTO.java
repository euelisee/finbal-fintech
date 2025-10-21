package com.fiap.finbal.dto;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensagemResponseDTO {

    private int status;
    private String mensagem;
    private Object dados;

    public MensagemResponseDTO(HttpStatus status, String mensagem, Object dados) {
        this.status = status.value();
        this.mensagem = mensagem;
        this.dados = dados;
    }

    public int getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Object getDados() {
        return dados;
    }
}