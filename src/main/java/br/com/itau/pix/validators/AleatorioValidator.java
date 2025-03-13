package br.com.itau.pix.validators;

import br.com.itau.pix.validators.interfaces.Aleatorio;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AleatorioValidator implements ConstraintValidator<Aleatorio, String>{

    private static final String REGEX = "^[a-zA-Z0-9]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(REGEX);
    }
}
