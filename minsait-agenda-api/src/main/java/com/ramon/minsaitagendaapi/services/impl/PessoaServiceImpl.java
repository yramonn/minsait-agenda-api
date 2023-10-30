package com.ramon.minsaitagendaapi.services.impl;

import com.ramon.minsaitagendaapi.exception.RegraNegocioException;
import com.ramon.minsaitagendaapi.models.Pessoa;
import com.ramon.minsaitagendaapi.repositories.PessoaRepository;
import com.ramon.minsaitagendaapi.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Override
    public Pessoa criarPessoa(Pessoa usuario) {
         return repository.save(usuario);
    }

    @Override
    public Optional<Pessoa> getByPessoaId(Long id) {
        return repository.findById(id);

    }

    @Override
    public List<Pessoa> getAllPessoas() {
        return repository.findAll();
    }


    @Override
    public void atualizarPessoa(Long id, Pessoa pessoaAtualizada) {
        Optional<Pessoa> pessoaExistenteOptional = repository.findById(id);

        if (pessoaExistenteOptional.isPresent()) {
            Pessoa pessoaExistente = pessoaExistenteOptional.get();

            pessoaExistente.setNome(pessoaAtualizada.getNome());
            pessoaExistente.setEndereco(pessoaAtualizada.getEndereco());
            pessoaExistente.setCep(pessoaAtualizada.getCep());
            pessoaExistente.setCidade(pessoaAtualizada.getCidade());
            pessoaExistente.setUf(pessoaAtualizada.getUf());

            repository.save(pessoaExistente);
        } else {
            throw new RegraNegocioException("Pessoa não encontrada para o ID: " + id);
        }
    }


    @Override
    public void excluirPessoa(Long id) {
        Pessoa pessoa = repository.findById(id).orElseThrow(() ->
                new RegraNegocioException("Pessoa não encontrada para o ID: " + id));
        repository.delete(pessoa);
    }

    @Override
    public void validarPessoa(Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Informe seu Nome");
        }
    }

    @Override
    public String formatarMalaDireta(Pessoa pessoa) {
        StringBuilder enderecoFormatado = new StringBuilder();

        if (pessoa.getEndereco() != null && !pessoa.getEndereco().isEmpty()) {
            enderecoFormatado.append(pessoa.getEndereco());
        }

        if (pessoa.getCep() != null && !pessoa.getCep().isEmpty()) {
            if (enderecoFormatado.length() > 0) {
                enderecoFormatado.append(" - ");
            }
            enderecoFormatado.append(pessoa.getCep());
        }

        if (pessoa.getCidade() != null && !pessoa.getCidade().isEmpty()) {
            if (enderecoFormatado.length() > 0) {
                enderecoFormatado.append(", ");
            }
            enderecoFormatado.append(pessoa.getCidade());
        }

        if (pessoa.getUf() != null && !pessoa.getUf().isEmpty()) {
            if (enderecoFormatado.length() > 0) {
                enderecoFormatado.append(" / ");
            }
            enderecoFormatado.append(pessoa.getUf());
        }

        return enderecoFormatado.toString();
    }


}
