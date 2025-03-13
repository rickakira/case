package br.com.itau.pix.services;

import br.com.itau.pix.converters.ChaveConverter;
import br.com.itau.pix.converters.interfaces.Converter;
import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.enums.Status;
import br.com.itau.pix.repositories.ChaveRepository;
import br.com.itau.pix.repositories.specifications.ChaveSpecification;
import br.com.itau.pix.validators.interfaces.Exists;
import br.com.itau.pix.validators.interfaces.LimiteChave;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@Service
@Transactional
@RequiredArgsConstructor
public class ChaveService {

    private final ApplicationContext context;
    private final ChaveRepository repository;
    private final ChaveConverter converter;

    @Valid
    public UUID save(@NotNull @Exists @LimiteChave String idPessoa, @Valid @NotNull ChaveDTO dto) {
        PixStrategy strategy = (PixStrategy) context.getBean(dto.getTipoChave());

        return strategy.save(idPessoa, dto);
    }

    public Optional<ChaveDTO> delete(@NotNull String id) {
        var entity = repository.findById(UUID.fromString(id));

        Optional<ChaveDTO> chaveDTO = Optional.empty();
        if (entity.isPresent()) {
            var chave = entity.get();
            chave.setDataDesativacao(LocalDate.now());
            chave.setStatus(Status.INATIVO);
            repository.save(chave);

            chaveDTO = Optional.of(chave).map(converter);
        }

        return chaveDTO;
    }

    public List<ChaveDTO> getChavesBy(@NotNull String idPessoa) {
        var specification = ChaveSpecification.filterBy(idPessoa);
        var entities = repository.findAll(specification);

        if (CollectionUtils.isEmpty(entities)) {
            return List.of();
        }
        return Converter.toList(entities, converter);
    }

}
