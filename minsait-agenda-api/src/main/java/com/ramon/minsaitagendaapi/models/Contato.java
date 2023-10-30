package com.ramon.minsaitagendaapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ramon.minsaitagendaapi.enums.TipoContato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_contato")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoContato tipoContato; // Enum: TELEFONE ou CELULAR

    @Column(nullable = false)
    private String contato;

    @ManyToOne
    @JoinColumn(name = "pessoa_id", nullable = false)
    @JsonIgnore
    private Pessoa pessoa;

}
