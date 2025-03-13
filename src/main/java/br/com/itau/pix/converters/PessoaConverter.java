package br.com.itau.pix.converters;

import br.com.itau.pix.converters.interfaces.Converter;
import br.com.itau.pix.converters.interfaces.DtoConverter;
import br.com.itau.pix.dtos.PessoaDTO;
import br.com.itau.pix.entities.Pessoa;
import br.com.itau.pix.enums.TipoConta;
import br.com.itau.pix.enums.TipoPessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PessoaConverter implements DtoConverter<Pessoa, PessoaDTO> {

    private final ChaveConverter chaveConverter;

    @Override
    public Pessoa toEntity(PessoaDTO dto) {
        return Pessoa.of()
                .tipoPessoa(Converter.to(dto.getTipoPessoa(), TipoPessoa::valueOf))
                .tipoConta(Converter.to(dto.getTipoConta(), TipoConta::valueOf))
                .nome(dto.getNome())
                .sobrenome(dto.getSobrenome())
                .conta(dto.getConta())
                .agencia(dto.getAgencia())
                .build();
    }

    @Override
    public PessoaDTO toDto(Pessoa entity) {
        return PessoaDTO.of()
                .id(entity.getId().toString())
                .tipoPessoa(entity.getTipoPessoa().getTipo())
                .tipoConta(entity.getTipoConta().getTipo())
                .agencia(entity.getAgencia())
                .conta(entity.getConta())
                .nome(entity.getNome())
                .sobrenome(entity.getSobrenome())
                .chaves(Converter.toList(entity.getChaves(), chaveConverter))
                .build();
    }
}
