package br.com.itau.pix.validators.interfaces;

import br.com.itau.pix.validators.ExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {

    String message() default "Registro não encontrado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

