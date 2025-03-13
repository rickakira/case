package br.com.itau.pix.validators;

import br.com.itau.pix.validators.interfaces.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String>{

    private static final String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(REGEX);
    }
}
