package algafood2api.infrastructure.repository;

import algafood2api.domain.model.Restaurante;
import algafood2api.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public List<Restaurante> buscaPorIntervalo(BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
//        var jpql = "from Restaurante where taxaFrete between :taxaFreteInicial and :taxaFreteFinal";
//
//        return entityManager.createQuery(jpql, Restaurante.class)
//                .setParameter("taxaFreteInicial", taxaFreteInicial)
//                .setParameter("taxaFreteFinal", taxaFreteFinal)
//                .getResultList();
//    }
    //Criteria API
    @Override
    public List<Restaurante> buscaPorIntervalo(BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);

        List<Predicate> predicates = new ArrayList<>();
        //Dinamismo
        if (taxaFreteFinal != null && taxaFreteFinal != null) {
            predicates.add(criteriaBuilder.between(root.get("taxaFrete"), taxaFreteInicial, taxaFreteFinal));
        } else if (taxaFreteInicial != null) {
            //Apenas exemplo, não tem lógica!
            predicates.add(criteriaBuilder.equal(root.get("taxaFrete"), taxaFreteInicial));
        } else {
            //Apenas exemplo, não tem lógica!
            predicates.add(criteriaBuilder.equal(root.get("taxaFrete"), taxaFreteFinal));
        }

        // Predicate taxaFretePredicate = criteriaBuilder.between(root.get("taxaFrete"), taxaFreteInicial, taxaFreteFinal);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
