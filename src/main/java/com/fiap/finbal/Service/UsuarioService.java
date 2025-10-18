package com.fiap.finbal.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fiap.finbal.Model.Usuario;
import com.fiap.finbal.Repository.UsuarioRepository;

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
                    usuarioExistente.setNome(usuarioAtualizado.getNome());
                    usuarioExistente.setSobrenome(usuarioAtualizado.getSobrenome());
                    usuarioExistente.setEmail(usuarioAtualizado.getEmail());
                    usuarioExistente.setSenha(usuarioAtualizado.getSenha());
                    usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
                    usuarioExistente.setCpf(usuarioAtualizado.getCpf());
                    return repository.save(usuarioExistente);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deletarUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado para exclusão");
        }
        repository.deleteById(id);
    }

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

}