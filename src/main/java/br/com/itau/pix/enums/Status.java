package br.com.itau.pix.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Status {

    ATIVO(1, "ATIVO"),
    INATIVO(0, "INATIVO");

    private final int status;
    private final String descricao;

    public static Status from(String descricao) {
        return Stream.of(Status.values()).filter(s -> Objects.equals(s.getDescricao(), descricao))
                .findAny()
                .orElse(null);
    }
}
