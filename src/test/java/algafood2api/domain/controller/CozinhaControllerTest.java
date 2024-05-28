package algafood2api.domain.controller;


import algafood2api.domain.model.Cozinha;
import algafood2api.domain.repository.CozinhaRepository;
import algafood2api.domain.service.CozinhaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CozinhaControllerTest {

    @InjectMocks
    CozinhaController cozinhaController;

    @Mock
    CozinhaService cozinhaService;

    @Mock
    CozinhaRepository cozinhaRepository;

    MockMvc mockMvc;

    List<Cozinha> cozinhasMock = new ArrayList<>();

    Optional<Cozinha> cozinhaMock;

    Long idKitchenSuccess = 1L;

    Long idKitchenNotFound = 999L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cozinhaController)
                .alwaysDo(print())
                .build();

        cozinhaMock = Optional.of(new Cozinha());
        cozinhaMock.get().setId(1L);
        cozinhaMock.get().setNome("Mineira");
        cozinhasMock.add(cozinhaMock.get());
    }

    @Test
    void shouldFindAllKitchensSuccess() throws Exception {

        when(cozinhaRepository.findAll())
                .thenReturn(cozinhasMock);

        mockMvc.perform(get("/cozinhas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Mineira"));

    }

    @Test
    void shouldFindByIdKitchenSucess() throws Exception {
        when(cozinhaRepository.findById(1L)).thenReturn(cozinhaMock);

        mockMvc.perform(get("/cozinhas/{}".replace("{}", idKitchenSuccess.toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void shouldFindByIdKitchenBadRequest() throws Exception {

        Optional<Cozinha> cozinha = Optional.empty();

        when(cozinhaRepository.findById(999L)).thenReturn(cozinha);

        mockMvc.perform(get("/cozinhas/{}".replace("{}", idKitchenNotFound.toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


}
