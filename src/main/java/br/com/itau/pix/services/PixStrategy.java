package br.com.itau.pix.services;

import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.entities.Chave;
import br.com.itau.pix.enums.Status;
import br.com.itau.pix.enums.TipoChave;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.UUID;

@Validated
public interface PixStrategy {

    UUID save(@NotNull String idPessoa, @Valid @NotNull ChaveDTO dto);

    default void tipoChave(Chave entity, TipoChave tipoChave) {
        entity.setTipoChave(tipoChave);
    }

    default void ativo(Chave entity) {
        entity.setDataInclusao(LocalDate.now());
        entity.setStatus(Status.ATIVO);
    }

}
