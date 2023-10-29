package com.ramon.minsaitagendaapi.controllers;

import com.ramon.minsaitagendaapi.models.Contato;
import com.ramon.minsaitagendaapi.models.Pessoa;
import com.ramon.minsaitagendaapi.repositories.ContatoRepository;
import com.ramon.minsaitagendaapi.repositories.PessoaRepository;
import com.ramon.minsaitagendaapi.services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contatos")

public class ContatoController {

    @Autowired
    ContatoRepository contatoRepository;
    PessoaRepository pessoaRepository;


    @GetMapping("/{id}")
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

    @GetMapping("/{idPessoa}/contatos")
    public ResponseEntity<?> listarContatosDaPessoa(@PathVariable Long idPessoa) {
        List<Contato> contatos = contatoRepository.findByPessoaId(idPessoa);

        if (contatos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhum contato encontrado para o ID da pessoa: " + idPessoa);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(contatos);
        }
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<?> deletarContato(@PathVariable Long id) {
        try {
            boolean existeContato = contatoRepository.existsById(id);
            if (existeContato) {
                contatoRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
