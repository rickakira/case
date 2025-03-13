package br.com.itau.pix.validators;

import br.com.itau.pix.dtos.PessoaSearch;
import br.com.itau.pix.validators.interfaces.PessoaSearchValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PessoaSearchValidator implements ConstraintValidator<PessoaSearchValid, PessoaSearch>{

    @Override
    public boolean isValid(PessoaSearch value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value.dataInativacao()) && Objects.nonNull(value.dataInclusao())) {
            return false;
        }

        return onlyId(value);
    }

    public static boolean onlyId(PessoaSearch value) {
        if (Objects.isNull(value.id())) {
            return true;
        }
        boolean isExists = Objects.nonNull(value.agencia()) ||
                 Objects.nonNull(value.conta()) ||
                 Objects.nonNull(value.tipoChave()) ||
                 Objects.nonNull(value.nomeCorrentista()) ||
                 Objects.nonNull(value.dataInclusao()) ||
                 Objects.nonNull(value.dataInativacao());

        return !isExists;
    }


}

