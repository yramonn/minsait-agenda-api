package com.ramon.minsaitagendaapi.controllers;

import com.ramon.minsaitagendaapi.models.Contato;
import com.ramon.minsaitagendaapi.repositories.ContatoRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/contatos")

public class ContatoController {

    @Autowired
    ContatoRepository contatoRepository;

    @GetMapping("/{id}")
    @Operation(summary = "Retorna os dados de um Contato por ID")
    public ResponseEntity<?> getContatoById(@PathVariable Long id) {
        Optional<Contato> contatoOptional = contatoRepository.findById(id);

        if (contatoOptional.isPresent()) {
            Contato contato = contatoOptional.get();
            return ResponseEntity.status(HttpStatus.OK).body(contato);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contato não encontrado para o ID: " + id);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um Contato existente")
    public ResponseEntity<?> atualizarContato(@PathVariable Long id, @RequestBody Contato novoContato) {
        Optional<Contato> contatoExistenteOptional = contatoRepository.findById(id);

        if (contatoExistenteOptional.isPresent()) {
            Contato contatoExistente = contatoExistenteOptional.get();
            contatoExistente.setTipoContato(novoContato.getTipoContato());
            contatoExistente.setContato(novoContato.getContato());

            Contato contatoAtualizado = contatoRepository.save(contatoExistente);
            return ResponseEntity.status(HttpStatus.OK).body(contatoAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Contato não encontrado para o ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um Contato por ID")
    public ResponseEntity<?> deletarContato(@PathVariable Long id) {
        try {
            boolean existeContato = contatoRepository.existsById(id);
            if (existeContato) {
                contatoRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("Contato deletado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Contato não encontrado para o ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir o contato para o ID: " + id);
        }

    }
}
