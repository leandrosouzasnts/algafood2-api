package algafood2api.domain.controller;

import algafood2api.domain.model.Cozinha;
import algafood2api.domain.repository.CozinhaRepository;
import algafood2api.domain.service.CozinhaService;
import lombok.Data;
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
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

        if (cozinha.isPresent()) {
           cozinha.get().setNome(model.getNome());
           return ResponseEntity.ok(cozinhaRepository.save(cozinha.get()));
        }  else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
        if (cozinha.isPresent()){
            cozinhaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}