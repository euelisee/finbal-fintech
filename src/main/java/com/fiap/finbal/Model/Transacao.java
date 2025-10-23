package com.fiap.finbal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fiap.finbal.Model.Categoria;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="USUARIO_ID")
    private com.fiap.finbal.model.Usuario usuario;

    @Column(name = "VALOR")
    private Double valor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="CATEGORIA")
    private Set<Categoria> categoria;

    @Column(name="DATA")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public com.fiap.finbal.model.Usuario getUsuario() {return usuario;}
    public void setUsuario(com.fiap.finbal.model.Usuario usuario) {this.usuario = usuario;}
    public Double getValor() {return valor;}
    public void setValor(Double valor) {this.valor = valor;}
    public Set<Categoria> getCategoria() {return categoria;}
    public void setCategoria(Set<Categoria> categoria) {this.categoria = categoria;}
    public LocalDate getData() {return data;}
    public void setData(LocalDate data) {this.data = data;}
}
