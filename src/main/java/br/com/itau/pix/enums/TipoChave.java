package br.com.itau.pix.enums;

import br.com.itau.pix.validators.interfaces.groups.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum TipoChave {

    CELULAR("celular", CelularGroup.class),
    EMAIL("email", EmailGroup.class),
    CPF("cpf", CPFGroup.class),
    CNPJ("cnpj", CNPJGroup.class),
    ALEATORIO("aleatorio", AleatorioGroup.class);

    private final String tipo;
    private final Class<?> group;


    public static TipoChave from(String value) {
        return Arrays.stream(TipoChave.values()).filter(t -> Objects.equals(t.tipo, value)).findAny().orElse(null);
    }
}
