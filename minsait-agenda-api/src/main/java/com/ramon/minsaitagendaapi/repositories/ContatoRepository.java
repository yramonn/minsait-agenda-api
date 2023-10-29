package com.ramon.minsaitagendaapi.repositories;

import com.ramon.minsaitagendaapi.models.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    @Query("SELECT c FROM Contato c WHERE c.pessoa.id = :idPessoa")
    List<Contato> findByPessoaId(@Param("idPessoa") Long idPessoa);

    @Query("SELECT c FROM Contato c WHERE c.id = :id AND c.pessoa.id = :idPessoa")
    Optional<Contato> findByIdAndPessoaId(@Param("id") Long id, @Param("idPessoa") Long idPessoa);
}
