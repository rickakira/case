package br.com.itau.pix.dtos;


import br.com.itau.pix.dtos.interfaces.Json;
import br.com.itau.pix.enums.TipoConta;
import br.com.itau.pix.validators.interfaces.ValidaEnum;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@JsonRootName("PessoaJson")
@Schema(description = "Pessoa")
@Builder(builderMethodName = "of")
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO implements Json {

    private String id;

    @NotEmpty(message = "O nome do correntista é obrigatório")
    @Size(max = 30, message = "O nome do correntista deve ter até 30 caracteres")
    private String nome;

    @Size(max = 45, message = "O sobrenome do correntista deve ter até 45 caracteres")
    private String sobrenome;

    @Enumerated(EnumType.STRING)
    private String tipoPessoa;

    @Enumerated(EnumType.STRING)
    @ValidaEnum(message = "Valor inválido para o Tipo de Conta. Somente são permitidos os seguintes valores: CORRENTE / POUPANÇA", enumClass = TipoConta.class)
    @NotNull(message = "O tipo de conta é obrigatório")
    private String tipoConta;

    @NotNull(message = "A agência é obrigatória")
    @Min(value = 0, message = "A agência deve ter um número válido")
    @Digits(integer = 4, fraction = 0, message = "A agência deve ter até 4 digítos")
    private Integer agencia;

    @NotNull(message = "A conta é obrigatória")
    @Min(value = 0, message = "A agência deve ter um número válido")
    @Digits(integer = 8, fraction = 0, message = "A conta deve ter até 8 digítos")
    private Integer conta;

    private List<ChaveDTO> chaves;

    public String toString() {
        return "PessoaDTO(id=" + this.getId() + ", nome=" + this.getNome() + ", sobrenome=" + this.getSobrenome() + ", tipoPessoa=" + this.getTipoPessoa() + ", tipoConta=" + this.getTipoConta() + ", agencia=" + this.getAgencia() + ", conta=" + this.getConta() + ", chaves=" + this.getChaves() + ")";
    }
}
