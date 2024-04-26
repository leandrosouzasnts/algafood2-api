package algafood2api.domain.controller;

import algafood2api.domain.exceptions.EntidadeNaoEncontradaException;
import algafood2api.domain.model.Cozinha;
import algafood2api.domain.model.Restaurante;
import algafood2api.domain.repository.RestauranteRepository;
import algafood2api.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listarTodos(){
        return ResponseEntity.ok(restauranteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(restauranteService.buscarPorId(id));
        }catch (EntidadeNaoEncontradaException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Restaurante> adicionar(@RequestBody Restaurante model){
        try {
            Restaurante restaurante = restauranteService.adicionar(model);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(409).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante){
        try {
           return ResponseEntity.ok(restauranteService.atualizar(id, restaurante));
        }catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        restauranteService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Restaurante> atualizarParcialmente(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        try {
            Restaurante restaurante = restauranteService.buscarPorId(id);

            merge(campos, restaurante);

            return adicionar(restaurante);
        } catch (EntidadeNaoEncontradaException ex ){
            return ResponseEntity.notFound().build();
        }
    }

    private void merge(Map<String, Object> campos, Restaurante restaurante){
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante origem = objectMapper.convertValue(campos, Restaurante.class);

        campos.forEach((property, value) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, property);
            field.setAccessible(true);

            Object valor = ReflectionUtils.getField(field, origem);
            ReflectionUtils.setField(field, restaurante, valor);
        });
    }

    @GetMapping("/top2-nome")
    public ResponseEntity<List<Restaurante>> getTop2(@RequestParam("nome") String nome){
        return ResponseEntity.ok(restauranteService.getTop2(nome));
    }

    @GetMapping("/count-cozinha")
    public ResponseEntity<Integer> countByCozinha(@RequestParam("id") Long id){
        return ResponseEntity.ok(restauranteService.countByCozinhaId(id));
    }

    @GetMapping("/comValorFrete-cozinha")
    public ResponseEntity<List<Restaurante>> getComValorFrenteECozinha(@RequestParam("id") Long id){
        return ResponseEntity.ok(restauranteService.buscaRestaurantesComFreteECozinhaY(id));
    }

    @GetMapping("/com-intervalo-frete")
    public ResponseEntity<List<Restaurante>> buscarComIntervalo(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return ResponseEntity.ok(restauranteRepository.buscaPorIntervalo(taxaInicial, taxaFinal));
    }

}
