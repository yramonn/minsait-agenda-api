package com.ramon.minsaitagendaapi.repositories;

import com.ramon.minsaitagendaapi.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
