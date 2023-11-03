package com.ramon.minsaitagendaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaMalaDiretaDTO {
    private Long id;
    private String nome;
    private String malaDireta;

}
