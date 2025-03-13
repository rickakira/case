package br.com.itau.pix.converters.interfaces;

import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.enums.TipoChave;
import io.micrometer.common.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface Utils {

    default LocalDate parseData(String data) {
        return StringUtils.isNotEmpty(data) ? LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }

    default ChaveDTO of(String valor, TipoChave tipoChave) {
        return ChaveDTO.of()
                .valorChave(valor)
                .tipoChave(tipoChave.getTipo())
                .build();
    }
}
