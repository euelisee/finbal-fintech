package com.fiap.finbal.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Conta")

public class Conta {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID_CONTA")
    private Long idConta;
    @Column(name = "NUMERO_CONTA", nullable = false, unique = true)
    private String numeroConta;
    @Column(name = "AGENCIA", nullable = false)
    private String agencia;
    @Column(name = "SALDO", nullable = false)
    private BigDecimal saldo;
    @Column(name = "TIPO_CONTA", nullable = false)
    private String tipoConta;

    @OneToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;



    //Construtor padrão
    public Conta(){}

    public Conta(String agencia, String numeroConta, BigDecimal saldo, String tipoConta, Long usuario) {
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
    }

    //get e set
    public Long getId() {
        return idConta;
    }

    public void setId(Long id) {
        this.idConta = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
