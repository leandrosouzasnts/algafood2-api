package algafood2api.domain.repository;

import algafood2api.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    public List<Restaurante> buscaPorIntervalo(BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    public List<Restaurante> buscaComFreteGratis(String nome);
}
