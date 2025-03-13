package br.com.itau.pix.dtos;

import br.com.itau.pix.dtos.interfaces.Json;
import br.com.itau.pix.validators.interfaces.Aleatorio;
import br.com.itau.pix.validators.interfaces.Celular;
import br.com.itau.pix.validators.interfaces.Email;
import br.com.itau.pix.validators.interfaces.RegistroUnico;
import br.com.itau.pix.validators.interfaces.groups.*;
import br.com.itau.pix.validators.providers.TipoChaveProvider;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.springframework.validation.annotation.Validated;

@Getter
@JsonRootName("ChaveJson")
@Validated
@Builder(builderMethodName = "of")
@AllArgsConstructor
@NoArgsConstructor
@GroupSequenceProvider(TipoChaveProvider.class)
public class ChaveDTO implements Json {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @JsonIgnoreProperties
    private String tipoChave;

    @NotEmpty(message = "O valor da chave é obrigatório")
    @CPF(message = "O CPF informado deve ser um valor válido", groups = CPFGroup.class)
    @CNPJ(message = "O CNPJ informado deve ser um valor válido", groups = CNPJGroup.class)
    @Email(message = "O e-mail informado deve ser válido", groups = EmailGroup.class)
    @Celular(message = "O número do celular informado deve ser um número válido", groups = CelularGroup.class)
    @Aleatorio(message = "O valor informado não pode conter caracteres especiais, somente letras e números", groups = AleatorioGroup.class)
    @RegistroUnico(message = "O valor de chave informado já cadastrado")
    @Size(message = "O valor da chave deve ter no máximo 77 caracteres", max = 77, groups = AleatorioGroup.class)
    private String valorChave;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataInclusao;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dataDesativacao;



}
