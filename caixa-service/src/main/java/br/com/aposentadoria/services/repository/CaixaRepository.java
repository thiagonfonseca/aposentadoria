package br.com.aposentadoria.services.repository;

import br.com.aposentadoria.services.entity.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    Optional<List<Caixa>> findByCpf(String cpf);

}
