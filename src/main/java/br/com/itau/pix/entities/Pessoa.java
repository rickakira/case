package br.com.itau.pix.entities;

import br.com.itau.pix.enums.TipoConta;
import br.com.itau.pix.enums.TipoPessoa;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "PESSOA")
@Builder(builderMethodName = "of")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Builder.Default
    private UUID id = null;

    @Column(name = "tipo_pessoa", length = 8)
    private TipoPessoa tipoPessoa;

    @Column(name = "nome", nullable = false, length = 30)
    private String nome;

    @Column(name = "sobrenome", length = 45)
    private String sobrenome;

    @Column(name = "tipo_conta", length = 8)
    private TipoConta tipoConta;

    @Column(name = "agencia", nullable = false, length = 4)
    private Integer agencia;

    @Column(name = "conta", nullable = false, length = 8)
    private Integer conta;

    @Builder.Default
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chave> chaves = new ArrayList<>();

    public void addChave(Chave chave) {
        chaves.add(chave);
    }

}
