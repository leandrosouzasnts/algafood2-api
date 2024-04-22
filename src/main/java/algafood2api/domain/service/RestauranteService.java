package algafood2api.domain.service;

import algafood2api.domain.exceptions.EntidadeNaoEncontradaException;
import algafood2api.domain.model.Cozinha;
import algafood2api.domain.model.Restaurante;
import algafood2api.domain.repository.CozinhaRepository;
import algafood2api.domain.repository.RestauranteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Restaurante buscarRestaurante(Long id){
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);

        if (restaurante.isPresent()){
            return restaurante.get();
        } else {
            throw new EntidadeNaoEncontradaException("Restaurante não encontrado");
        }
    }

    public List<Restaurante> buscarTodos(){
        return restauranteRepository.findAll();
    }

    public Restaurante buscarPorId(Long id){
        return buscarRestaurante(id);
    }

    public Restaurante adicionar(Restaurante restaurante) {
        Optional<Cozinha> cozinha = cozinhaRepository.findById(restaurante.getCozinha().getId());
        if(cozinha.isEmpty()) {
            throw new DataIntegrityViolationException("Cozinha não existente");
        }
        restaurante.setCozinha(cozinha.get());
        return restauranteRepository.save(restaurante);
    }

    public void remover(Long id){
        Restaurante restaurante = buscarRestaurante(id);
        restauranteRepository.delete(restaurante);
    }

    public Restaurante atualizar(Long id, Restaurante model){
        Restaurante restaurante = buscarRestaurante(id);

        modelMapper.map(model, restaurante);

        restaurante.setId(id);
        return adicionar(restaurante);
    }
}
