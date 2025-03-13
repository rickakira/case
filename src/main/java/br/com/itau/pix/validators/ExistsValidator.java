package br.com.itau.pix.validators;

import br.com.itau.pix.enums.TipoPessoa;
import br.com.itau.pix.repositories.ChaveRepository;
import br.com.itau.pix.repositories.PessoaRepository;
import br.com.itau.pix.validators.interfaces.Exists;
import br.com.itau.pix.validators.interfaces.LimiteChave;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExistsValidator implements ConstraintValidator<Exists, String>{

    private final PessoaRepository repository;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        var uuid = UUID.fromString(id);

        var found = repository.findById(uuid);

        return found.isPresent();
    }

}
