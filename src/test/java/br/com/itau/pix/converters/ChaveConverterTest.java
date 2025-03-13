package br.com.itau.pix.converters;

import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.entities.Chave;
import br.com.itau.pix.enums.Status;
import br.com.itau.pix.enums.TipoChave;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ChaveConverterTest {

    @Autowired
    private ChaveConverter converter;

    private final Faker faker = new Faker();

    @Test
    void toEntity() {
        var dto = dto();

        var chave = converter.toEntity(dto);

        assertEquals(dto.getId(), chave.getId().toString());
        assertEquals(dto.getValorChave(), chave.getValorChave());
        assertEquals(dto.getStatus(), chave.getStatus().toString());
        assertEquals(dto.getTipoChave(), chave.getTipoChave().getTipo());
        assertEquals(dto.getDataInclusao(), chave.getDataInclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertEquals(dto.getDataDesativacao(), chave.getDataDesativacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Test
    void toDto() {
        var chave = entity();

        var dto = converter.toDto(chave);

        assertEquals(chave.getId().toString(), dto.getId());
        assertEquals(chave.getValorChave(), dto.getValorChave());
        assertEquals(chave.getStatus().toString(), dto.getStatus());
        assertEquals(chave.getTipoChave().getTipo(), dto.getTipoChave());
        assertEquals(chave.getDataInclusao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dto.getDataInclusao());
        assertEquals(chave.getDataDesativacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), dto.getDataDesativacao());
    }


    private ChaveDTO dto() {
        return ChaveDTO.of()
                .id(UUID.randomUUID().toString())
                .tipoChave(TipoChave.ALEATORIO.getTipo())
                .valorChave(faker.random().hex(6))
                .status(Status.ATIVO.getDescricao())
                .dataInclusao(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .dataDesativacao(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
    }

    private Chave entity() {
        return Chave.of()
                .id(UUID.randomUUID())
                .valorChave(faker.random().hex(6))
                .status(Status.ATIVO)
                .tipoChave(TipoChave.ALEATORIO)
                .dataInclusao(LocalDate.now())
                .dataDesativacao(LocalDate.now())
                .build();
    }
}