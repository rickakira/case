package br.com.itau.pix.controller;

import br.com.itau.pix.enums.TipoConta;
import br.com.itau.pix.repositories.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ChaveControllerTest implements PessoaDTOBuild{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private ObjectMapper mapper;

    private final String URL_PESSOA = "/pessoas";
    private final String URL = "/chaves";

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void testSaveAleatorio() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testSaveCelular() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");
        mockMvc.perform(post(URL + "/celular/pessoa/" + uuid + "?chave=+5511987654321")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testSaveCNPJ() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/cnpj/pessoa/" + uuid + "/chave/" + dataFaker.cnpj().valid(false))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testSaveCPF() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/cpf/pessoa/" + uuid + "/chave/" + dataFaker.cpf().valid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testSaveEmail() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/email/pessoa/" + uuid + "/chave/teste@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testGetAll() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/email/pessoa/" + uuid + "/chave/teste@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        mockMvc.perform(get(URL + "/pessoa/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    void testErrorAleatorio() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(99))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testErrorCelular() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/celular/pessoa/" + uuid + "?chave=9911987654321")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testErrorCNPJ() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/cnpj/pessoa/" + uuid + "/chave/" + dataFaker.cnpj().invalid(false))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testErrorCPF() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/cpf/pessoa/" + uuid + "/chave/" + dataFaker.cpf().invalid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testErrorEmail() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL + "/email/pessoa/" + uuid + "/chave/" + dataFaker.name().firstName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testDelete() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL_PESSOA + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        var postResult = mockMvc.perform(post(URL + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String id = postResult.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(delete(URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}