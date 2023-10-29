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


//    @Override
//    public void Pessoa atualizarPessoa(Pessoa pessoaAtualizada) {
//
//        Optional<Pessoa> pessoaExistente = repository.findById(pessoaAtualizada.getId());
//
//        if(pessoaExistente.isPresent()) {
//            Pessoa novaPessoa = pessoaExistente.get();
//            novaPessoa.setNome(pessoaAtualizada.getNome());
//            novaPessoa.setEndereco(pessoaAtualizada.getEndereco());
//            novaPessoa.setCep(pessoaAtualizada.getCep());
//            novaPessoa.setCidade(pessoaAtualizada.getCidade());
//            novaPessoa.setUf(pessoaAtualizada.getUf());
//
//            return repository.save(novaPessoa);
//        }
//        return pessoaAtualizada;
//    }

    @Override
    public void  atualizarPessoa(Pessoa pessoaAtualizada) throws RegraNegocioException {

        Pessoa pessoaExistente = repository.findById(pessoaAtualizada.getId()).orElse(null);

        if (pessoaExistente == null) {
            throw new RegraNegocioException("Pessoa não encontrada para o ID: " + pessoaAtualizada.getId());
        }
        pessoaExistente.setNome(pessoaAtualizada.getNome());
        pessoaExistente.setEndereco(pessoaAtualizada.getEndereco());
        pessoaExistente.setCep(pessoaAtualizada.getCep());
        pessoaExistente.setCidade(pessoaAtualizada.getCidade());
        pessoaExistente.setUf(pessoaAtualizada.getUf());

             repository.save(pessoaExistente);
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
}
