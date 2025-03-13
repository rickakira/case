package br.com.itau.pix.converters;

import br.com.itau.pix.converters.interfaces.Converter;
import br.com.itau.pix.converters.interfaces.DtoConverter;
import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.entities.Chave;
import br.com.itau.pix.enums.Status;
import br.com.itau.pix.enums.TipoChave;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ChaveConverter implements DtoConverter<Chave, ChaveDTO> {

    @Override
    public Chave toEntity(ChaveDTO dto) {
        return Chave.of()
                .id(Converter.to(dto.getId(), UUID::fromString))
                .status(Converter.to(dto.getStatus(), Status::from))
                .tipoChave(Converter.to(dto.getTipoChave(), TipoChave::from))
                .valorChave(dto.getValorChave())
                .dataInclusao(toDate(dto.getDataInclusao()))
                .dataDesativacao(toDate(dto.getDataDesativacao()))
                .build();
    }

    @Override
    public ChaveDTO toDto(Chave entity) {
        return ChaveDTO.of()
                .id(entity.getId().toString())
                .tipoChave(entity.getTipoChave().getTipo())
                .valorChave(entity.getValorChave())
                .dataInclusao(retornaData(entity.getDataInclusao()))
                .dataDesativacao(retornaData(entity.getDataDesativacao()))
                .status(entity.getStatus().getDescricao())
                .build();
    }
}
