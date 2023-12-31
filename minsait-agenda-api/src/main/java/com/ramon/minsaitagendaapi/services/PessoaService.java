package com.ramon.minsaitagendaapi.services;

import com.ramon.minsaitagendaapi.models.Pessoa;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    Pessoa criarPessoa(Pessoa usuario);

    Optional<Pessoa> getByPessoaId(Long id);

    List<Pessoa> getAllPessoas();

    void validarPessoa(Pessoa usuario);

    void atualizarPessoa(Long id, Pessoa pessoaAtualizada);

    void excluirPessoa(Long id);

     String formatarMalaDireta(Pessoa pessoa);
}
