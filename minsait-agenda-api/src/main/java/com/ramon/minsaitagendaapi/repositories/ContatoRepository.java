package com.ramon.minsaitagendaapi.repositories;

import com.ramon.minsaitagendaapi.models.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
