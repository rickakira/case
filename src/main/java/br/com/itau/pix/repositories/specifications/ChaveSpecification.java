package br.com.itau.pix.repositories.specifications;

import br.com.itau.pix.entities.Chave;
import br.com.itau.pix.entities.Pessoa;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChaveSpecification {

    public static Specification<Chave> filterBy(String idPessoa) {
        return (root, query, criteria) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Chave, Pessoa> join = root.join("pessoa", JoinType.INNER);

            predicates.add(criteria.equal(join.get("id"), UUID.fromString(idPessoa)));

            return criteria.and(predicates.toArray(new Predicate[0]));
        };
    }
}
