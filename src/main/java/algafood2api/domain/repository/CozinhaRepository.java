package algafood2api.domain.repository;

import algafood2api.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    List<Cozinha> findByNome(String nome);

    List<Cozinha> findByNomeContaining(String nome);

    Boolean existsByNomeContaining(String nome);
}
