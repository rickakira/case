package br.com.itau.pix.validators.interfaces;

import br.com.itau.pix.validators.AleatorioValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AleatorioValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Aleatorio {

    String message() default "O valor da chave deve ser alfanumérico e sem caracteres especiais";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
