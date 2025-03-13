package br.com.itau.pix.services;

import br.com.itau.pix.converters.PessoaConverter;
import br.com.itau.pix.converters.interfaces.Converter;
import br.com.itau.pix.dtos.PessoaDTO;
import br.com.itau.pix.dtos.PessoaPatchDTO;
import br.com.itau.pix.entities.Pessoa;
import br.com.itau.pix.enums.TipoConta;
import br.com.itau.pix.enums.TipoPessoa;
import br.com.itau.pix.repositories.PessoaRepository;
import br.com.itau.pix.repositories.specifications.PessoaSpecification;
import br.com.itau.pix.dtos.PessoaSearch;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@Service
@RequiredArgsConstructor
@Transactional
public class PessoaService {

    private final PessoaRepository repository;
    private final PessoaConverter converter;


    public UUID savePF(@Valid @NotNull PessoaDTO dto) {
        var entity = converter.toEntity(dto);
        this.tipoPessoa(entity, TipoPessoa.FISICA);

        repository.save(entity);

        return entity.getId();
    }

    public UUID savePJ(@Valid @NotNull PessoaDTO dto) {
        var entity = converter.toEntity(dto);
        this.tipoPessoa(entity, TipoPessoa.JURIDICA);

        repository.save(entity);

        return entity.getId();
    }

    public Optional<PessoaDTO> update(@NotNull String id, @Valid @NotNull PessoaPatchDTO dto) {
        Optional<PessoaDTO> pessoaDTO = Optional.empty();

        var entity = repository.findById(UUID.fromString(id));

        if (entity.isPresent()) {
            var pessoa = entity.get();
            pessoa.setTipoConta(Converter.to(dto.getTipoConta(), TipoConta::valueOf));
            pessoa.setNome(dto.getNome());
            pessoa.setSobrenome(dto.getSobrenome());
            pessoa.setAgencia(dto.getAgencia());
            pessoa.setConta(dto.getConta());
            repository.save(pessoa);

            pessoaDTO = Optional.of(pessoa).map(converter);
        }

        return pessoaDTO;
    }

    public Optional<PessoaDTO> getById(@Valid @NotNull PessoaSearch search) {
        return repository.findById(UUID.fromString(search.id())).map(converter::toDto);
    }


    public List<PessoaDTO> getBy(@Valid @NotNull PessoaSearch search) {
        Specification<Pessoa> specification = PessoaSpecification.filterBy(search);
        var pessoaList = repository.findAll(specification);
        var list = repository.findAll();
        if (CollectionUtils.isEmpty(pessoaList)) {
            return List.of();
        }

        return Converter.toList(pessoaList, converter);
    }

    private void tipoPessoa(Pessoa entity, TipoPessoa tipoPessoa) {
        entity.setTipoPessoa(tipoPessoa);
    }

}
