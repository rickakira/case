package br.com.itau.pix.validators;

import br.com.itau.pix.repositories.ChaveRepository;
import br.com.itau.pix.validators.interfaces.RegistroUnico;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistroUnicoValidator implements ConstraintValidator<RegistroUnico, String>{


    private final ChaveRepository repository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }

        return !repository.existsByValorChave(value);
    }
}
