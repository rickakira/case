package br.com.itau.pix.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoConta {

    CORRENTE("CORRENTE"),
    POUPANCA("POUPANCA");

    private final String tipo;
}
