package com.ramon.minsaitagendaapi.controllers;

import com.ramon.minsaitagendaapi.exception.RegraNegocioException;
import com.ramon.minsaitagendaapi.models.Contato;
import com.ramon.minsaitagendaapi.models.Pessoa;
import com.ramon.minsaitagendaapi.repositories.ContatoRepository;
import com.ramon.minsaitagendaapi.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")

public class PessoaController {

    @Autowired
    private PessoaService service;
    @Autowired
    private ContatoRepository contatoRepository;


    @PostMapping
    public ResponseEntity criarPessoa(@RequestBody Pessoa pessoa) {

        try {
            Pessoa novaPessoa = service.criarPessoa(pessoa);
            service.validarPessoa(pessoa);
            return new ResponseEntity(novaPessoa, HttpStatus.CREATED);

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterPessoaPorId(@PathVariable Long id) {
        try {
            Optional<Pessoa> pessoa = service.getByPessoaId(id);

            if (pessoa == null || pessoa.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Pessoa não encontrada para o ID: " + id);
            }

            return ResponseEntity.ok(pessoa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Pessoa não encontrada para o ID, tente novamente!");
        }
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas(){
        List<Pessoa> pessoas = service.getAllPessoas();
        if(pessoas == null || pessoas.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pessoas);
    }

    @PutMapping("/{id}/contatos")
    public ResponseEntity<?> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa){

        try {
            Optional<Pessoa> novaPessoa = service.getByPessoaId(id);
            if (novaPessoa  == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Pessoa não encontrada para o ID: " + id);
            }
            service.atualizarPessoa(pessoa);
            return ResponseEntity.ok("Pessoa atualizada com sucesso.");

        } catch (RegraNegocioException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
 }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPessoa(@PathVariable Long id) {
        try {
            service.excluirPessoa(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pessoa deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao deletar pessoa, o ID não existe!");
        }
    }

    @PostMapping("/{id}/contatos")
    public ResponseEntity<Contato> adicionarContato(@PathVariable Long id, @RequestBody Contato novoContato) {
        Optional<Pessoa> pessoaOptional = service.getByPessoaId(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            novoContato.setPessoa(pessoa);
            Contato contatoSalvo = contatoRepository.save(novoContato);
            return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
