package com.fiap.finbal.Controller;

import com.fiap.finbal.Model.Conta;
import com.fiap.finbal.Service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fiap.finbal.DTO.ContaCreationDTO;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
//@CrossOrigin(origins = "http://localhost:5173")
public class ContaController {
    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService){
        this.contaService = contaService;
    }

    //Create
    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestBody ContaCreationDTO contaDto) {
        Conta novaConta = contaService.criarConta(contaDto);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    //Read
    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaService.listarTodasContas();
        return ResponseEntity.ok(contas);
    }

    //Read
    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarContaPorId(@PathVariable Long id) {
        return contaService.buscarContaPorId(id)
                .map(conta -> ResponseEntity.ok(conta))
                .orElse(ResponseEntity.notFound().build());
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta contaDetalhes){
        return contaService.atualizarConta(id, contaDetalhes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id){
        if(contaService.deletarConta(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    
}
