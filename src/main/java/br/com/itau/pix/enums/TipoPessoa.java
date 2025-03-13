package br.com.itau.pix.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoPessoa {

    FISICA("FISICA"),
    JURIDICA("JURIDICA");

    private final String tipo;

    public boolean isFisica() {
        return this == FISICA;
    }
}
