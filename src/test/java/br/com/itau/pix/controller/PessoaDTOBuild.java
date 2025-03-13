package br.com.itau.pix.controller;

import br.com.itau.pix.dtos.PessoaDTO;
import br.com.itau.pix.dtos.PessoaPatchDTO;
import br.com.itau.pix.enums.TipoConta;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public interface PessoaDTOBuild {

    Faker dataFaker = new Faker();

    default PessoaDTO build() {
        return build(null,null,null,null,null);
    }

    default PessoaDTO build(TipoConta tipoConta) {
        return build(null,null, tipoConta.getTipo(), null, null);
    }

    default PessoaDTO build(String nome, String sobrenome, String tipoConta, Integer agencia, Integer conta) {

        return PessoaDTO.of()
                .nome(Optional.ofNullable(nome).orElse(dataFaker.name().firstName()))
                .sobrenome(Optional.ofNullable(sobrenome).orElse(dataFaker.name().lastName()))
                .tipoConta(tipoConta)
                .agencia(Optional.ofNullable(agencia).orElse(dataFaker.number().numberBetween(1, 9999)))
                .conta(Optional.ofNullable(conta).orElse(dataFaker.number().numberBetween(1, 99999999)))
                .build();
    }

    default List<PessoaDTO> buildEntities(int count) {
        return IntStream.rangeClosed(0, count).mapToObj(i ->
                build(TipoConta.CORRENTE)).toList();
    }

    default PessoaPatchDTO buildPatch() {
        return buildPatch(null, null, null, null, null);
    }

    default PessoaPatchDTO buildPatch(TipoConta tipoConta) {
        return buildPatch(null, null, tipoConta.getTipo(), null, null);
    }

    default PessoaPatchDTO buildPatch(String nome, String sobrenome, String tipoConta, Integer agencia, Integer conta) {
       return PessoaPatchDTO.of()
               .nome(Optional.ofNullable(nome).orElse(dataFaker.name().firstName()))
               .sobrenome(Optional.ofNullable(sobrenome).orElse(dataFaker.name().lastName()))
               .tipoConta(tipoConta)
               .agencia(Optional.ofNullable(agencia).orElse(dataFaker.number().numberBetween(1, 9999)))
               .conta(Optional.ofNullable(conta).orElse(dataFaker.number().numberBetween(1, 99999999)))
               .build();
    }
}
