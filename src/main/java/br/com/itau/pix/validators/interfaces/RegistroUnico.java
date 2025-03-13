package br.com.itau.pix.validators.interfaces;

import br.com.itau.pix.validators.RegistroUnicoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegistroUnicoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RegistroUnico {

    String message() default "Registro já existente";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
