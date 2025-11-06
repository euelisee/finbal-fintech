package com.fiap.finbal.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiap.finbal.Model.TipoTransacao;
import com.fiap.finbal.model.Categoria;


import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoRequestDTO(
        TipoTransacao tipo,
        BigDecimal valor,
        Categoria categoria,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate data,
        Long contaId
) {
}
