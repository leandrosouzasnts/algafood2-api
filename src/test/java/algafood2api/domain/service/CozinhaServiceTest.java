package algafood2api.domain.service;

import algafood2api.domain.model.Cozinha;
import algafood2api.domain.repository.CozinhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CozinhaServiceTest {

    @InjectMocks
    CozinhaService cozinhaService;

    @Mock
    CozinhaRepository cozinhaRepository;

    Cozinha cozinha;

    @BeforeEach
    public void setUp() {
        cozinha = new Cozinha();

        cozinha.setNome("Mineira");
        cozinha.setId(1L);
    }

    @Test
    void shouldReturnKitchensByName() {

        when(cozinhaService.buscarPorNome("Mineira")).thenReturn(Collections.singletonList(cozinha));

        List<Cozinha> cozinhas = cozinhaService.buscarPorNome("Mineira");

        verify(cozinhaRepository).findByNomeContaining("Mineira");
        assertEquals(Collections.singletonList(cozinha), cozinhas);
    }

}
