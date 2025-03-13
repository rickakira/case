package br.com.itau.pix.repositories.specifications;

import br.com.itau.pix.entities.Chave;
import br.com.itau.pix.entities.Pessoa;
import br.com.itau.pix.dtos.PessoaSearch;
import br.com.itau.pix.enums.Status;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PessoaSpecification {

    public static Specification<Pessoa> filterBy(PessoaSearch search) {
        return (root, query, criteria) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Pessoa, Chave> join = root.join("chaves", JoinType.LEFT);

            if (StringUtils.isNotEmpty(search.tipoChave())) {
                predicates.add(criteria.equal(join.get("tipoChave"), search.tipoChave()));
            }

            if (StringUtils.isNotEmpty(search.nomeCorrentista())) {
                predicates.add(criteria.like(root.get("nome"), "%" + search.nomeCorrentista() + "%"));
            }

            if (Objects.nonNull(search.agencia())) {
                predicates.add(criteria.equal(root.get("agencia"), search.agencia()));
            }

            if (Objects.nonNull(search.conta())) {
                predicates.add(criteria.equal(root.get("conta"), search.conta()));
            }

            if (Objects.nonNull(search.dataInclusao())) {
                predicates.add(criteria.equal(join.get("dataInclusao"), search.dataInclusao()));
            }

            if (Objects.nonNull(search.dataInativacao())) {
                predicates.add(criteria.equal(join.get("dataDesativacao"), search.dataInativacao()));
            }

            if (Objects.isNull(search.dataInativacao())) {
                predicates.add(criteria.equal(join.get("status"), Status.ATIVO.getStatus()));
            }
            return criteria.and(predicates.toArray(new Predicate[0]));
        };
    }
}
