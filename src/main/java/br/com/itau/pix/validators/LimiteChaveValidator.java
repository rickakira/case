package br.com.itau.pix.validators;

import br.com.itau.pix.enums.TipoPessoa;
import br.com.itau.pix.repositories.ChaveRepository;
import br.com.itau.pix.repositories.PessoaRepository;
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
public class LimiteChaveValidator implements ConstraintValidator<LimiteChave, String>{

    private final PessoaRepository pessoaRepository;
    private final ChaveRepository chaveRepository;

    private static final long LIMITE_PF = 5L;
    private static final long LIMITE_PJ = 20L;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        var uuid = UUID.fromString(id);

        TipoPessoa tipoPessoa = pessoaRepository.getByTipoPessoa(uuid);

        if (Objects.isNull(tipoPessoa)) {
            return false;
        }

        long registros = chaveRepository.countByPessoaId(uuid);

        return tipoPessoa.isFisica() ?
                registros < LIMITE_PF :
                registros < LIMITE_PJ;
    }
}
