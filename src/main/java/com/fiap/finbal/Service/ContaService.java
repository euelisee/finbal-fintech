package com.fiap.finbal.Service;

import com.fiap.finbal.DTO.ContaCreationDTO;
import com.fiap.finbal.Model.Conta;
import com.fiap.finbal.Model.Usuario;
import com.fiap.finbal.Repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiap.finbal.Repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ContaService(ContaRepository contaRepository, UsuarioRepository usuarioRepository){
        this.contaRepository = contaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    //Create
    public Conta criarConta(ContaCreationDTO contaDTO){
        //return contaRepository.save(conta);

        Usuario usuario = usuarioRepository.findById(contaDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID:" + contaDTO.getIdUsuario()));

        Conta novaConta = new Conta();
        novaConta.setNumeroConta(contaDTO.getNumeroConta());
        novaConta.setAgencia(contaDTO.getAgencia());
        novaConta.setSaldo(contaDTO.getSaldo());
        novaConta.setTipoConta(contaDTO.getTipoConta());

        novaConta.setUsuario(usuario);

        return contaRepository.save(novaConta);
    }

    //Read
    public List<Conta> listarTodasContas(){
        return contaRepository.findAll();
    }

    public Optional<Conta> buscarContaPorId(Long id){
        return contaRepository.findById(id);
    }

    //Update
    public Optional<Conta> atualizarConta(Long id, Conta detalhesConta){
        return contaRepository.findById(id).map(contaExistente -> {
            contaExistente.setAgencia(detalhesConta.getAgencia());
            contaExistente.setNumeroConta(detalhesConta.getNumeroConta());
            contaExistente.setSaldo(detalhesConta.getSaldo());
            contaExistente.setTipoConta(detalhesConta.getTipoConta());
            return contaRepository.save(contaExistente);
        });
    }

    //Delete
    public boolean deletarConta(Long id){
        if (contaRepository.existsById(id)){
            contaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
