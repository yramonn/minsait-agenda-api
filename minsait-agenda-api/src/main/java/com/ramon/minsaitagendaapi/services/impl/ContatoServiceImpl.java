package com.ramon.minsaitagendaapi.services.impl;

import com.ramon.minsaitagendaapi.models.Contato;
import com.ramon.minsaitagendaapi.models.Pessoa;
import com.ramon.minsaitagendaapi.repositories.ContatoRepository;
import com.ramon.minsaitagendaapi.services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ContatoServiceImpl implements ContatoService {

    @Autowired
    ContatoRepository repository;

    @Override
    public Optional<Contato> getByContatoId(Long id) {
        return repository.findById(id);
    }
}
