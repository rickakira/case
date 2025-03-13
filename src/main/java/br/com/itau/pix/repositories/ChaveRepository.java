package br.com.itau.pix.repositories;

import br.com.itau.pix.entities.Chave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChaveRepository extends JpaRepository<Chave, UUID>, JpaSpecificationExecutor<Chave> {

    long countByPessoaId(UUID pessoaId);

    boolean existsByValorChave(String valorChave);

    List<Chave> findAll();

}
