package br.com.itau.pix.services;

import br.com.itau.pix.converters.ChaveConverter;
import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.enums.TipoChave;
import br.com.itau.pix.repositories.ChaveRepository;
import br.com.itau.pix.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("cpf")
@RequiredArgsConstructor
public class CPFPix implements PixStrategy {

    private final PessoaRepository pessoaRepository;
    private final ChaveRepository chaveRepository;
    private final ChaveConverter converter;

    @Override
    public UUID save(String idPessoa, ChaveDTO dto) {
        var entity = converter.toEntity(dto);

        var pessoaEntity = pessoaRepository.findById(UUID.fromString(idPessoa));

        pessoaEntity.ifPresent(p -> {
            tipoChave(entity, TipoChave.CPF);
            ativo(entity);
            entity.setPessoa(p);
            p.addChave(entity);
            chaveRepository.save(entity);
        });

        return entity.getId();
    }

}
