package com.fiap.finbal.Controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException; // ⭐️ NOVO IMPORT ⭐️
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.finbal.dto.MensagemResponseDTO;
import com.fiap.finbal.exception.UsuarioException;
import com.fiap.finbal.Model.Usuario;
import com.fiap.finbal.Service.UsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") 
public class UsuarioController {

    private final UsuarioService service;

    private UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = service.listarUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = service.obterUsuarioPorId(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado"));
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }
    @PostMapping("/usuarios")
    public ResponseEntity<MensagemResponseDTO> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = service.save(usuario);
            MensagemResponseDTO resposta = new MensagemResponseDTO(
                    HttpStatus.CREATED,
                    "Usuário cadastrado com sucesso!",
                    novoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
            
        } catch (DataIntegrityViolationException e) {
            MensagemResponseDTO erro = new MensagemResponseDTO(
                    HttpStatus.CONFLICT,
                    "Erro de cadastro: CPF ou E-mail já existem. Tente com outros dados.",
                    null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
            
        } catch (RuntimeException e) {
            MensagemResponseDTO erro = new MensagemResponseDTO(
                    HttpStatus.BAD_REQUEST, 
                    e.getMessage(), 
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<MensagemResponseDTO> atualizarUsuarioPorId(
            @PathVariable Long id,
            @RequestBody Usuario usuarioAtualizado) {

        Usuario usuario = service.atualizarUsuario(id, usuarioAtualizado);
        MensagemResponseDTO resposta = new MensagemResponseDTO(
                HttpStatus.OK,
                "Usuário atualizado com sucesso!",
                usuario);

        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<MensagemResponseDTO> deletarUsuarioPorId(@PathVariable Long id) {
        try {
            service.deletarUsuario(id);
            MensagemResponseDTO resposta = new MensagemResponseDTO(
                    HttpStatus.OK,
                    "Usuário deletado com sucesso!",
                    null);
            return ResponseEntity.status(HttpStatus.OK).body(resposta);
        } catch (RuntimeException e) {
            MensagemResponseDTO erro = new MensagemResponseDTO(
                    HttpStatus.NOT_FOUND,
                    e.getMessage(), 
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }
    }

}