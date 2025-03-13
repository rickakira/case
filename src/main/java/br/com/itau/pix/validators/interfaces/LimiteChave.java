package br.com.itau.pix.validators.interfaces;

import br.com.itau.pix.validators.LimiteChaveValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LimiteChaveValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface LimiteChave {

    String message() default "Limite de chaves atingido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

