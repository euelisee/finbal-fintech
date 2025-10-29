package com.fiap.finbal.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fiap.finbal.Model.Conta;
import jakarta.persistence.*;

@Table(name = "TB_USUARIO")
@Entity
public class Usuario extends com.fiap.finbal.model.Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "SENHA")
    private String senha;
    @Column(name = "CPF", nullable = true)
    private String cpf;
    @Column(name = "DATA_CRIACAO")
    private LocalDate dataCriacao = LocalDate.now();
    @Column(name = "DATA_NASCIMENTO", nullable = true) 
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Conta conta;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


}
