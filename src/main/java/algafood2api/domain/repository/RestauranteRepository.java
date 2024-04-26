package algafood2api.domain.repository;

import algafood2api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries{

    List<Restaurante> getTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long id);

    //@Query("from Restaurante where taxaFrete > 0 and cozinha.id = :id")
    List<Restaurante> buscaRestaurantesComFreteECozinhaY(@Param("id") Long cozinha);
}