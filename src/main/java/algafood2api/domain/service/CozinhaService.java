package algafood2api.domain.service;

import algafood2api.domain.exceptions.EntidadeNaoEncontradaException;
import algafood2api.domain.model.Cozinha;
import algafood2api.domain.repository.CozinhaRepository;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class CozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha atualizar(Long id, Cozinha model){
        Cozinha cozinha = buscarCozinha(id);

        if (cozinha != null) {
            BeanUtils.copyProperties(model, cozinha, "id");
            return cozinhaRepository.save(cozinha);
        } else {
            throw new EntidadeNaoEncontradaException("Cozinha não encontrada");
        }
    }

    private Cozinha buscarCozinha(Long id){
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

        if (cozinha.isPresent()) {
            return cozinha.get();
        } else {
            return null;
        }
    }

    public void remover(Long id){
        Cozinha cozinha = buscarCozinha(id);
        if (cozinha != null) {
            cozinhaRepository.delete(cozinha);
        } else {
            throw new EntidadeNaoEncontradaException("Cozinha não encontrada");
        }
    }

    public List<Cozinha> buscarPorNome(String nome) {
        return cozinhaRepository.findByNomeContaining(nome);
    }
}
