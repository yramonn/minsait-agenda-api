package com.ramon.minsaitagendaapi.services;

import com.ramon.minsaitagendaapi.models.Contato;
import java.util.Optional;

public interface ContatoService {

    Optional<Contato> getByContatoId(Long id);

    void validarContato(Contato contato);


}
