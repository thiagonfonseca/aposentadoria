package br.com.aposentadoria.services.controller;

import br.com.aposentadoria.services.builder.BeneficiarioDTOBuilder;
import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.service.BeneficiarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static br.com.aposentadoria.services.utils.JsonConvertionUtils.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BeneficiarioControllerTest {

    private static final String API_URL_PATH = "/api/v1/beneficiarios/";
    private static final long INVALID_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private BeneficiarioService service;

    @InjectMocks
    private BeneficiarioController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenABeneficiarioIsCreated() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.saveBeneficiario(beneficiarioDTO)).thenReturn("Beneficiário " + beneficiarioDTO.getNome() + " cadastrado com sucesso!");

        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beneficiarioDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenPUTIsCalledThenABeneficiarioIsUpdated() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.updateBeneficiario(beneficiarioDTO.getId(), beneficiarioDTO))
                .thenReturn("Beneficiário " + beneficiarioDTO.getNome() + " atualizado com sucesso!");

        mockMvc.perform(put(API_URL_PATH + beneficiarioDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beneficiarioDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void whenPUTIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.updateBeneficiario(beneficiarioDTO.getId(), beneficiarioDTO))
                .thenThrow(DataNotFoundException.class);

        mockMvc.perform(put(API_URL_PATH + beneficiarioDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beneficiarioDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithBeneficiariosIsCalledThenOkStatusIsReturned() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.listAll()).thenReturn(Collections.singletonList(beneficiarioDTO));

        mockMvc.perform(get(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is(beneficiarioDTO.getNome())))
                .andExpect(jsonPath("$[0].cpf", is(beneficiarioDTO.getCpf())))
                .andExpect(jsonPath("$[0].email", is(beneficiarioDTO.getEmail())))
                .andExpect(jsonPath("$[0].saldo", is(beneficiarioDTO.getSaldo())))
                .andExpect(jsonPath("$[0].anos", is(beneficiarioDTO.getAnos())));
    }

    @Test
    void whenGETListWithoutBeneficiariosIsCalledThenOkStatusIsReturned() throws Exception {

        when(service.listAll()).thenReturn(Collections.EMPTY_LIST);

        mockMvc.perform(get(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.findById(beneficiarioDTO.getId())).thenReturn(beneficiarioDTO);

        mockMvc.perform(get(API_URL_PATH + beneficiarioDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(beneficiarioDTO.getNome())))
                .andExpect(jsonPath("$.cpf", is(beneficiarioDTO.getCpf())))
                .andExpect(jsonPath("$.email", is(beneficiarioDTO.getEmail())))
                .andExpect(jsonPath("$.saldo", is(beneficiarioDTO.getSaldo())))
                .andExpect(jsonPath("$.anos", is(beneficiarioDTO.getAnos())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {

        when(service.findById(INVALID_ID)).thenThrow(DataNotFoundException.class);

        mockMvc.perform(get(API_URL_PATH + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETIsCalledWithValidCpfThenOkStatusIsReturned() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.findByCPF(beneficiarioDTO.getCpf())).thenReturn(beneficiarioDTO);

        mockMvc.perform(get(API_URL_PATH + "cpf/" + beneficiarioDTO.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(beneficiarioDTO.getNome())))
                .andExpect(jsonPath("$.cpf", is(beneficiarioDTO.getCpf())))
                .andExpect(jsonPath("$.email", is(beneficiarioDTO.getEmail())))
                .andExpect(jsonPath("$.saldo", is(beneficiarioDTO.getSaldo())))
                .andExpect(jsonPath("$.anos", is(beneficiarioDTO.getAnos())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredCpfThenNotFoundStatusIsReturned() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.findByCPF(beneficiarioDTO.getCpf())).thenThrow(DataNotFoundException.class);

        mockMvc.perform(get(API_URL_PATH + "cpf/" + beneficiarioDTO.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        doNothing().when(service).delete(beneficiarioDTO.getId());

        mockMvc.perform(delete(API_URL_PATH + beneficiarioDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        doThrow(DataNotFoundException.class).when(service).delete(INVALID_ID);

        mockMvc.perform(delete(API_URL_PATH + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
