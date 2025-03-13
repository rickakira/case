package br.com.itau.pix.controller;

import br.com.itau.pix.enums.TipoChave;
import br.com.itau.pix.enums.TipoConta;
import br.com.itau.pix.repositories.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PessoaControllerTest implements PessoaDTOBuild{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final String URL = "/pessoas";
    private final String URL_CHAVES = "/chaves";

    @Test
    void testSavePFSuccess() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString();
        assertTrue(StringUtils.isNotEmpty(uuid));
    }

    @Test
    void testSavePJSuccess() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/juridica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString();
        assertTrue(StringUtils.isNotEmpty(uuid));
    }

    @Test
    void testUpdateSuccess() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"","");

        var patchDto = buildPatch(TipoConta.POUPANCA);
        String patchJson = mapper.writeValueAsString(patchDto);

        mockMvc.perform(put(URL + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(patchJson))
                .andExpect(status().isOk());
    }

    @Test
    void testRetornoUnprocessable() throws Exception {
        var dto = build("", "teste", TipoConta.POUPANCA.getTipo(),123, 123);
        String json = mapper.writeValueAsString(dto);

        mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testPostVazio() throws Exception {
        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        assertEquals("Não foi informado o RequestBody da chamada", results.getResponse().getContentAsString());
    }

    @Test
    void testGetById() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(get(URL + "?id=" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.nome").isNotEmpty());
    }

    @Test
    void testGetByTipoChave() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        mockMvc.perform(get(URL + "?tipoChave=" + TipoChave.ALEATORIO.getTipo())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByAgencia() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        mockMvc.perform(get(URL + "?agencia=" + dto.getAgencia())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testGetByConta() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        mockMvc.perform(get(URL + "?conta=" + dto.getConta())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testGetByNomeCorrentista() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        mockMvc.perform(get(URL + "?nomeCorrentista=" + dto.getNome())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testGetByDataInclusao() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        mockMvc.perform(get(URL + "?dataInclusao=" + data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testGetByDataInativacao() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        var response = mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andReturn();

        String id = response.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(delete(URL_CHAVES + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        mockMvc.perform(get(URL + "?dataInativacao=" + data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testGetByAgenciaConta() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        mockMvc.perform(get(URL + "?conta=" + dto.getConta() + "&agencia=" + dto.getAgencia())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testGetByTodosCampos() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        mockMvc.perform(get(URL + "?conta=" + dto.getConta() + "&agencia=" + dto.getAgencia() +
                        "&nomeCorrentista=" + dto.getNome() + "&tipoChave=" + TipoChave.ALEATORIO.getTipo() +
                        "&dataAtivacao=" + data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    void testGetByDataInclusaoDataInativacao() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);

        var results = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = results.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(post(URL_CHAVES + "/aleatorio/pessoa/" + uuid + "/chave/" + dataFaker.random().hex(50))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        mockMvc.perform(get(URL + "?dataInclusao=" + data + "&dataInativacao=" + data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void testGetByInvalido() throws Exception {
        var dto = build(TipoConta.CORRENTE);
        String json = mapper.writeValueAsString(dto);
        String msg = "[\"Os filtros passados não respeitam as seguintes regras de pesquisa:* Caso o ID seja informado, não podem ser preenchidos nenhum outro filtro de consulta;* Não é permitido a utilização dos filtros Data de inclusão da chave e Data da inativação da chave de forma conjunta.\"]";

        var response = mockMvc.perform(post(URL + "/fisica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String uuid = response.getResponse().getContentAsString().replace("\"", "");

        var results = mockMvc.perform(get(URL + "?id="+uuid+"&nomeCorrentista=teste", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        assertEquals(msg, results.getResponse().getContentAsString());
    }
}