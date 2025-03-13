package br.com.itau.pix.services;

import br.com.itau.pix.controller.PessoaDTOBuild;
import br.com.itau.pix.converters.PessoaConverter;
import br.com.itau.pix.dtos.PessoaDTO;
import br.com.itau.pix.dtos.PessoaPatchDTO;
import br.com.itau.pix.dtos.PessoaSearch;
import br.com.itau.pix.enums.TipoConta;
import br.com.itau.pix.repositories.PessoaRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PessoaServiceTest implements PessoaDTOBuild {

    @Autowired
    private PessoaService service;

    @Autowired
    private PessoaConverter converter;

    @Autowired
    private PessoaRepository repository;


    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void testSuccessSavePF() {
        var dto = build(TipoConta.CORRENTE);

        UUID id = service.savePF(dto);

        assertTrue(Objects.nonNull(id));
    }

    @Test
    void testNullSavePF() {
        assertThrows(ConstraintViolationException.class, () -> service.savePF(null),  "savePF.dto: não deve ser nulo");
    }


    @Test
    void testSuccessSavePJ() {
        var dto = build(TipoConta.POUPANCA);

        UUID id = service.savePJ(dto);

        assertTrue(Objects.nonNull(id));
    }

    @Test
    void testNullSavePJ() {
        assertThrows(ConstraintViolationException.class, () -> service.savePJ(null),  "savePJ.dto: não deve ser nulo");
    }

    @Test
    void testUpdate() {
        var dto = build(TipoConta.CORRENTE);

        UUID id = service.savePF(dto);

        var patch = buildPatch(TipoConta.POUPANCA);

        var entity = service.update(id.toString(), patch);

        assertTrue(entity.isPresent());
        assertEquals(TipoConta.POUPANCA.getTipo(), entity.map(PessoaDTO::getTipoConta).orElse(null));
    }

    @Test
    void testNullUpdate() {
        assertThrows(ConstraintViolationException.class, () -> service.update(null, null),  "update.id: não deve ser nulo; update.dto: não deve ser nulo");
    }

    @Test
    void testNullIdUpdate() {
        var dto = new PessoaPatchDTO();
        assertThrows(ConstraintViolationException.class, () -> service.update(null, dto),  "update.id: não deve ser nulo");
    }

    @Test
    void testNullDTOUpdate() {
        String id = UUID.randomUUID().toString();
        assertThrows(ConstraintViolationException.class, () -> service.update(id, null),  "update.dto: não deve ser nulo");
    }

    @Test
    void testGetById() {
        var ids = generateEntities(1);

        ids.forEach(id -> {
            var search = new PessoaSearch(id.toString(), null, null, null, null, null, null);

            var pessoa = service.getById(search);

            assertEquals(id.toString(), pessoa.map(PessoaDTO::getId).orElse(null));

        });

    }

    @Test
    void testNullGetById() {
        assertThrows(ConstraintViolationException.class, () -> service.getById(null),  "getById.search: não deve ser nulo");
    }

    private List<UUID> generateEntities(int count) {
        List<UUID> list = new ArrayList<>();

        buildEntities(count).forEach(dto -> {
            list.add(service.savePF(dto));
        });

        return list;
    }

}