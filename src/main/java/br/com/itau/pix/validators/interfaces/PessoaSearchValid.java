package br.com.itau.pix.validators.interfaces;

import br.com.itau.pix.validators.PessoaSearchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PessoaSearchValidator.class)
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PessoaSearchValid {

    String message() default "Os filtros passados não respeitam as seguintes regras de pesquisa:* Caso o ID seja informado, não podem ser preenchidos nenhum outro filtro de consulta;* Não é permitido a utilização dos filtros Data de inclusão da chave e Data da inativação da chave de forma conjunta.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
