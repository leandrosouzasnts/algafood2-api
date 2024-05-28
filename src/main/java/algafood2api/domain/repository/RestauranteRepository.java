package algafood2api.domain.repository;

import algafood2api.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends CustomRepository<Restaurante, Long>,
        RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> getTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long id);

    //@Query("from Restaurante where taxaFrete > 0 and cozinha.id = :id")
    List<Restaurante> buscaRestaurantesComFreteECozinhaY(@Param("id") Long cozinha);

    //Todo  - Anotar (Quando sabemos que vamos precisar de forma imediata, usamos fetch pra evitar consultas desnecessárias, caso contrário mantemos apenas o Join
    // Visto que só será chamado quando necessário
    @Query("from Restaurante r join fetch r.cozinha")
    List<Restaurante> buscarTodos();

}