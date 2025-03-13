package br.com.itau.pix.services;

import br.com.itau.pix.controller.PessoaDTOBuild;
import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.enums.Status;
import br.com.itau.pix.enums.TipoChave;
import br.com.itau.pix.enums.TipoConta;
import br.com.itau.pix.repositories.ChaveRepository;
import br.com.itau.pix.repositories.PessoaRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
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
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ChaveServiceTest implements PessoaDTOBuild {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ChaveService service;

    @Autowired
    private ChaveRepository repository;

    @BeforeEach
    void clean() {
        pessoaRepository.deleteAll();
    }

    @Test
    void testSaveAleatorio() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dto = of(dataFaker.random().hex(77), TipoChave.ALEATORIO);
        var idChave = service.save(idPessoa.toString(), dto);

        assertTrue(Objects.nonNull(idChave));
    }

    @Test
    void testSizeAleatorio() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();

        String random = dataFaker.random().hex(88);
        var dto = of(random, TipoChave.ALEATORIO);
        assertThrows(ConstraintViolationException.class, () -> service.save(idPessoa, dto), "O valor da chave deve ter no máximo 77 caracteres");
    }

    @Test
    void testCharsAleatorio() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();

        String random = "A123Ç?ewH90!@#";
        var dto = of(random, TipoChave.ALEATORIO);
        assertThrows(ConstraintViolationException.class, () -> service.save(idPessoa, dto), "O valor informado não pode conter caracteres especiais, somente letras e números");
    }

    @Test
    void testSaveCPF() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dto = of(dataFaker.cpf().valid(), TipoChave.CPF);
        var idChave = service.save(idPessoa.toString(), dto);

        assertTrue(Objects.nonNull(idChave));
    }

    @Test
    void testInvalidCPF() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();

        var dto = of(dataFaker.cpf().invalid(), TipoChave.CPF);
        assertThrows(ConstraintViolationException.class, () -> service.save(idPessoa, dto), "O CPF informado deve ser um valor válido");
    }

    @Test
    void testSaveCNPJ() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dto = of(dataFaker.cnpj().valid(), TipoChave.CNPJ);
        var idChave = service.save(idPessoa.toString(), dto);

        assertTrue(Objects.nonNull(idChave));
    }

    @Test
    void testInvalidCNPJ() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();

        var dto = of(dataFaker.cnpj().invalid(), TipoChave.CNPJ);
        assertThrows(ConstraintViolationException.class, () -> service.save(idPessoa, dto), "O CNPJ informado deve ser um valor válido");
    }

    @Test
    void testSaveEmail() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dto = of("teste@email.com", TipoChave.EMAIL);
        var idChave = service.save(idPessoa.toString(), dto);

        assertTrue(Objects.nonNull(idChave));
    }

    @Test
    void testInvalidEmail() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();

        var dto = of("teste", TipoChave.EMAIL);
        assertThrows(ConstraintViolationException.class, () -> service.save(idPessoa, dto), "O e-mail informado deve ser válido");
    }

    @Test
    void testSaveCelular() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dto = of("+5511999999999", TipoChave.CELULAR);
        var idChave = service.save(idPessoa.toString(), dto);

        assertTrue(Objects.nonNull(idChave));
    }

    @Test
    void testInvalidCelular() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();

        var dto = of("55559999999", TipoChave.CELULAR);
        assertThrows(ConstraintViolationException.class, () -> service.save(idPessoa, dto), "O número do celular informado deve ser um número válido");
    }

    @Test
    void testDuplicate() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dto = of(dataFaker.random().hex(50), TipoChave.ALEATORIO);

        assertThrows(ConstraintViolationException.class, () -> {
            service.save(idPessoa.toString(), dto);
            service.save(idPessoa.toString(), dto);
        }, "O valor de chave informado já cadastrado");
    }


    @Test
    void testGetChaves() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dto = of(dataFaker.random().hex(77), TipoChave.ALEATORIO);
        service.save(idPessoa.toString(), dto);

        List<ChaveDTO> chaves = service.getChavesBy(idPessoa.toString());

        assertFalse(chaves.isEmpty());
    }

    @Test
    void testGetChavesEmpty() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);

        List<ChaveDTO> chaves = service.getChavesBy(idPessoa.toString());

        assertTrue(chaves.isEmpty());
    }

    @Test
    void testLimitSizePF() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dtos = buildChaves(5);

        var ids = new ArrayList<>();
        dtos.forEach(dto -> ids.add(service.save(idPessoa.toString(), dto)));

        assertEquals(5, ids.size());
    }

    @Test
    void testMaiorLimitSizePF() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO);
        var dtos = buildChaves(6);

        assertThrows(ConstraintViolationException.class, () -> {
            dtos.forEach(dto -> service.save(idPessoa.toString(), dto));
        }, "save.idPessoa: Limite de chaves atingido");
    }

    @Test
    void testLimitSizePJ() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePJ(pessoaDTO);
        var dtos = buildChaves(20);

        var ids = new ArrayList<>();
        dtos.forEach(dto -> ids.add(service.save(idPessoa.toString(), dto)));

        assertEquals(20, ids.size());
    }

    @Test
    void testMaiorLimitSizePJ() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePJ(pessoaDTO);
        var dtos = buildChaves(21);

        assertThrows(ConstraintViolationException.class, () -> {
            dtos.forEach(dto -> service.save(idPessoa.toString(), dto));
        }, "save.idPessoa: Limite de chaves atingido");
    }


    @Test
    void testNullValor() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();

        var dto = of(null, TipoChave.ALEATORIO);
        assertThrows(ConstraintViolationException.class, () -> service.save(idPessoa, dto), "O valor da chave é obrigatório");
    }

    @Test
    void testNull() {
        assertThrows(ValidationException.class, () -> service.save(null, null), "save.idPessoa: não deve ser nulo; save.dto: não deve ser nulo");
    }

    @Test
    void testNullIdPessoa() {
        var chaveDTO = new ChaveDTO();
        assertThrows(ValidationException.class, () -> service.save(null, chaveDTO), "save.idPessoa: não deve ser nulo");
    }

    @Test
    void testNullDto() {
        var id = UUID.randomUUID().toString();
        assertThrows(ConstraintViolationException.class, () -> service.save(id, null), "save.dto: não deve ser nulo");
    }

    @Test
    void testBadFormatCelular() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();
        var dto = of("+AA11999999999", TipoChave.CELULAR);

        assertThrows(ConstraintViolationException.class, () -> service.save(idPessoa, dto), "O número do celular informado deve ser um número válido");
    }

    @Test
    void delete() {
        var pessoaDTO = build(TipoConta.CORRENTE);

        var idPessoa = pessoaService.savePF(pessoaDTO).toString();
        var dto = of(dataFaker.random().hex(10), TipoChave.ALEATORIO);

        var id = service.save(idPessoa, dto);

        var result = service.delete(id.toString());

        assertEquals(Status.INATIVO.getDescricao(), result.map(ChaveDTO::getStatus).orElse(null));
    }

    @Test
    void deleteIdPessoaUnknown() {
        var dto = of(dataFaker.random().hex(10), TipoChave.ALEATORIO);

        assertThrows(ConstraintViolationException.class, () -> service.save(UUID.randomUUID().toString(), dto), "save.idPessoa: Registro não encontrado");
    }

    private ChaveDTO of(String valorChave, TipoChave tipoChave) {
        return ChaveDTO.of()
                .tipoChave(tipoChave.getTipo())
                .valorChave(valorChave)
                .build();
    }

    private List<ChaveDTO> buildChaves(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> of(dataFaker.number().digits(i), TipoChave.ALEATORIO))
                .toList();
    }
}