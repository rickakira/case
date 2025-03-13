package br.com.itau.pix.converters.interfaces;

import br.com.itau.pix.dtos.interfaces.Json;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public interface DtoConverter<T, K extends Json> extends Converter<T, K> {
    T toEntity(K dto);

    K toDto(T entity);

    default K convert(T entity) {
        return this.toDto(entity);
    }

    default String retornaData(LocalDate data) {
        return Objects.nonNull(data) ? data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    default LocalDate toDate(String data) {
        return Objects.nonNull(data) ? LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }
}
