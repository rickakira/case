package br.com.itau.pix.repositories;

import br.com.itau.pix.entities.Pessoa;
import br.com.itau.pix.enums.TipoPessoa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, UUID>, JpaSpecificationExecutor<Pessoa> {

    @Query("select tipoPessoa from Pessoa where id = :id")
    TipoPessoa getByTipoPessoa(@Param("id") UUID id);
}
