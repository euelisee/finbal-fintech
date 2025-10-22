package com.fiap.finbal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fiap.finbal.model.Usuario;
import com.fiap.finbal.repository.UsuarioRepository;
import com.fiap.finbal.exception.UsuarioException; 

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Optional<Usuario> obterUsuarioPorId(Long id) {
        return repository.findById(id);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        return repository.findById(id)
                .map(usuarioExistente -> {
                    if (usuarioAtualizado.getNome() != null) {
                        usuarioExistente.setNome(usuarioAtualizado.getNome());
                    }
                    if (usuarioAtualizado.getSobrenome() != null) {
                        usuarioExistente.setSobrenome(usuarioAtualizado.getSobrenome());
                    }
                    if (usuarioAtualizado.getEmail() != null) {
                        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
                    }
                
                    String novaSenha = usuarioAtualizado.getSenha();
                    if (novaSenha != null && !novaSenha.isEmpty() && !novaSenha.equals("********")) {
                        usuarioExistente.setSenha(novaSenha);
                    }
                    
                    if (usuarioAtualizado.getTelefone() != null) {
                        usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
                    }
                    if (usuarioAtualizado.getCpf() != null) {
                        usuarioExistente.setCpf(usuarioAtualizado.getCpf());
                    }
                    if (usuarioAtualizado.getDataNascimento() != null) {
                         usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());
                    }

                    return repository.save(usuarioExistente);
                })
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado para atualização"));
    }
    public void deletarUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioException("Usuário não encontrado para deleção");
        }
        repository.deleteById(id);
    }
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }
}