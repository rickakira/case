package br.com.itau.pix.entities;

import br.com.itau.pix.enums.Status;
import br.com.itau.pix.enums.TipoChave;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "CHAVE")
@Builder(builderMethodName = "of")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chave implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "tipo_chave", nullable = false, length = 9)
    @Enumerated(EnumType.STRING)
    private TipoChave tipoChave;

    @Column(name = "valor_chave", nullable = false, length = 77)
    private String valorChave;

    @Column(name = "status", length = 1)
    private Status status;

    @Column(name = "data_inclusao")
    private LocalDate dataInclusao;

    @Column(name = "data_desativacao")
    private LocalDate dataDesativacao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;
}
