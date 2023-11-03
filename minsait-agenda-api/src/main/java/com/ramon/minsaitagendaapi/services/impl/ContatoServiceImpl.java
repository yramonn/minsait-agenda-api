package com.ramon.minsaitagendaapi.services.impl;

import com.ramon.minsaitagendaapi.exception.RegraNegocioException;
import com.ramon.minsaitagendaapi.models.Contato;
import com.ramon.minsaitagendaapi.repositories.ContatoRepository;
import com.ramon.minsaitagendaapi.services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContatoServiceImpl implements ContatoService {

    @Autowired
    ContatoRepository repository;

    @Override
    public Optional<Contato> getByContatoId(Long id) {
        return repository.findById(id);
    }

    @Override
    public void validarContato(Contato contato) {
        if (contato.getContato() == null || contato.getContato().trim().isEmpty()) {
            throw new RegraNegocioException("Informe seu Contato");
        }
    }
}
