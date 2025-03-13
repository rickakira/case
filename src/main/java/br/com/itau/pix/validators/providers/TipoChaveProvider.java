package br.com.itau.pix.validators.providers;

import br.com.itau.pix.dtos.ChaveDTO;
import br.com.itau.pix.enums.TipoChave;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TipoChaveProvider implements DefaultGroupSequenceProvider<ChaveDTO> {

    @Override
    public List<Class<?>> getValidationGroups(ChaveDTO dto) {
        List<Class<?>> groups = new ArrayList<>();

        groups.add(ChaveDTO.class);
        if (Objects.isNull(dto) || Objects.isNull(dto.getTipoChave())) {
            return groups;
        }

        var tipoChave = TipoChave.from(dto.getTipoChave());

        groups.add(tipoChave.getGroup());

        return groups;
    }
}
