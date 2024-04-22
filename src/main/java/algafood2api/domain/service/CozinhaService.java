package algafood2api.domain.service;

import algafood2api.domain.model.Cozinha;
import algafood2api.domain.repository.CozinhaRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class CozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

}
