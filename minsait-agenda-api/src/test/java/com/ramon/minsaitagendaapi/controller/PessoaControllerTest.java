package com.ramon.minsaitagendaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramon.minsaitagendaapi.controllers.PessoaController;
import com.ramon.minsaitagendaapi.models.Pessoa;
import com.ramon.minsaitagendaapi.services.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PessoaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pessoaController).build();
    }

    @Test
    public void testObterPessoaPorIdPessoaExistente() throws Exception {
        Long pessoaId = 1L;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaId);
        pessoa.setNome("Ramon Dev");

        when(pessoaService.getByPessoaId(anyLong())).thenReturn(Optional.of(pessoa));

        mockMvc.perform(get("/api/pessoas/{id}", pessoaId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(pessoaId))
                .andExpect(jsonPath("$.nome").value("Ramon Dev"));
    }


    @Test
    public void testCriarPessoaComSucesso() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Ramon Dev");

        when(pessoaService.criarPessoa(any())).thenReturn(pessoa);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(pessoa);

        mockMvc.perform(post("/api/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Ramon Dev"));
    }

    @Test
    public void testListarPessoas() throws Exception {
        List<Pessoa> pessoas = Arrays.asList(new Pessoa(), new Pessoa());

        when(pessoaService.getAllPessoas()).thenReturn(pessoas);

        mockMvc.perform(get("/api/pessoas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(pessoas)));
    }

    @Test
    public void testAtualizarPessoaComSucesso() throws Exception {
        Long pessoaId = 1L;
        Pessoa pessoa = new Pessoa();

        mockMvc.perform(put("/api/pessoas/{id}", pessoaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(status().isOk())
                .andExpect(content().string("Pessoa atualizada com sucesso."));

        verify(pessoaService).atualizarPessoa(pessoaId, pessoa);
    }

    @Test
    public void testExcluirPessoaComSucesso() throws Exception {
        Long pessoaId = 1L;

        mockMvc.perform(delete("/api/pessoas/{id}", pessoaId))
                .andExpect(status().isOk())
                .andExpect(content().string("Pessoa deletada com sucesso."));

        verify(pessoaService).excluirPessoa(pessoaId);
    }




}
