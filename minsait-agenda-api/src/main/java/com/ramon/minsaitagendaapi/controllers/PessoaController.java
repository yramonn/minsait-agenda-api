package com.ramon.minsaitagendaapi.controllers;

import com.ramon.minsaitagendaapi.dto.PessoaMalaDiretaDTO;
import com.ramon.minsaitagendaapi.exception.RegraNegocioException;
import com.ramon.minsaitagendaapi.models.Contato;
import com.ramon.minsaitagendaapi.models.Pessoa;
import com.ramon.minsaitagendaapi.repositories.ContatoRepository;
import com.ramon.minsaitagendaapi.services.ContatoService;
import com.ramon.minsaitagendaapi.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Autowired
    private ContatoService contatoService;



    @PostMapping
    @Operation(summary = "Cria uma nova Pessoa")
    public ResponseEntity criarPessoa(@RequestBody Pessoa pessoa) {

        try {
            service.validarPessoa(pessoa);
            Pessoa novaPessoa = service.criarPessoa(pessoa);
            return new ResponseEntity(novaPessoa, HttpStatus.CREATED);

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna os dados de uma Pessoa por ID")
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
    @Operation(summary = "Lista todas as Pessoas")
    public ResponseEntity<List<Pessoa>> listarPessoas(){
        List<Pessoa> pessoas = service.getAllPessoas();
        if(pessoas == null || pessoas.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pessoas);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma Pessoa existente")
    public ResponseEntity<?> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        try {
            service.atualizarPessoa(id, pessoa);
            return ResponseEntity.ok("Pessoa atualizada com sucesso.");
        } catch (RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar a pessoa para o ID: " + id);
        }
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma Pessoa por ID")
    public ResponseEntity<String> excluirPessoa(@PathVariable Long id) {
        try {
            service.excluirPessoa(id);
            return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao deletar pessoa, o ID não existe!");
        }
    }

    @PostMapping("/{id}/contatos")
    @Operation(summary = "Adiciona um novo Contato a uma Pessoa")
    public ResponseEntity<Contato> adicionarContato(@PathVariable Long id, @RequestBody Contato novoContato) {
        Optional<Pessoa> pessoaOptional = service.getByPessoaId(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            novoContato.setPessoa(pessoa);
            contatoService.validarContato(novoContato);
            Contato contatoSalvo = contatoRepository.save(novoContato);
            return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{idPessoa}/contatos")
    @Operation(summary = "Lista todos os Contatos de uma Pessoa")
    public ResponseEntity<?> listarContatosDaPessoa(@PathVariable Long idPessoa) {
        List<Contato> contatos = contatoRepository.findByPessoaId(idPessoa);

        if (contatos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhum contato encontrado para o ID da pessoa: " + idPessoa);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(contatos);
        }
    }

    @GetMapping("maladireta/{id}")
    @Operation(summary = "Retorna os dados de uma Pessoa por ID para mala direta")
    public ResponseEntity<?> getPessoaParaMalaDireta(@PathVariable Long id) {
        try {
            Optional<Pessoa> pessoaOptional = service.getByPessoaId(id);
            if (pessoaOptional.isPresent()) {
                Pessoa pessoa = pessoaOptional.get();
                String malaDireta = service.formatarMalaDireta(pessoa);
                PessoaMalaDiretaDTO pessoaMalaDiretaDTO = new PessoaMalaDiretaDTO(id, pessoa.getNome(), malaDireta);
                return ResponseEntity.status(HttpStatus.OK).body(pessoaMalaDiretaDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Pessoa não encontrada para o ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar a pessoa para o ID: " + id);
        }
    }

}
