package com.fiap.finbal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiap.finbal.model.Categoria;
import jakarta.persistence.*;
import com.fiap.finbal.Model.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Table(name = "TB_TRANSACAO")
@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CONTA_ID")
    private Conta conta;

    @Column(name= "TIPO")
    private String tipo;

    @Column(name = "VALOR")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORIA")
    private Categoria categoria;

    @Column(name="DATA")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Conta getConta() {return conta;}
    public void setConta(Conta conta) {this.conta = conta;}
    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}
    public BigDecimal getValor() {return valor;}
    public void setValor(BigDecimal valor) {this.valor = valor;}
    public Categoria getCategoria() {return categoria;}
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}
    public LocalDate getData() {return data;}
    public void setData(LocalDate data) {this.data = data;}
}
