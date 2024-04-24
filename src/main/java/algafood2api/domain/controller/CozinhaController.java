package algafood2api.domain.controller;

import algafood2api.domain.exceptions.EntidadeNaoEncontradaException;
import algafood2api.domain.model.Cozinha;
import algafood2api.domain.repository.CozinhaRepository;
import algafood2api.domain.service.CozinhaService;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cozinhas")
@Data
public class CozinhaController {

    //Fazendo Injeção de Dependencia via Construtor @Data
    private final CozinhaRepository cozinhaRepository;

    private final CozinhaService cozinhaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cozinha>> findAll(){
        return ResponseEntity.ok(cozinhaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> findById(@PathVariable Long id){
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha model){
        return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaService.salvar(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> update(@PathVariable Long id, @RequestBody Cozinha model){
        try {
            return ResponseEntity.ok(cozinhaService.atualizar(id, model));
        }catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            cozinhaService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Cozinha>> buscarPorNome(@RequestParam("nome") String nome){
        return ResponseEntity.ok(cozinhaService.buscarPorNome(nome));
    }

    @GetMapping("/existe-nome")
    public ResponseEntity<Boolean> existsCozinha(@RequestParam("nome") String nome){
        return ResponseEntity.ok(cozinhaRepository.existsByNomeContaining(nome));
    }
}
