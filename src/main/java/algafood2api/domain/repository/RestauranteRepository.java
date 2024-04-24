package algafood2api.domain.repository;

import algafood2api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> getTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long id);
}
