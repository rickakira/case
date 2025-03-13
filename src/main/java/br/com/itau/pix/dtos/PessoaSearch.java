package br.com.itau.pix.dtos;

import br.com.itau.pix.validators.interfaces.PessoaSearchValid;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@PessoaSearchValid
public record PessoaSearch(String id,
                           String tipoChave,
                           Integer agencia,
                           Integer conta,
                           String nomeCorrentista,
                           LocalDate dataInclusao,
                           LocalDate dataInativacao) {
}
