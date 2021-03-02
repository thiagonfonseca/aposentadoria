package br.com.aposentadoria.services.controller;

import br.com.aposentadoria.services.builder.BeneficiarioDTOBuilder;
import br.com.aposentadoria.services.builder.CaixaDTOBuilder;
import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.dto.CaixaDTO;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.service.CaixaService;
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
public class CaixaControllerTest {

    private static final String API_URL_PATH = "/api/v1/caixa/";
    private static final long INVALID_ID = 2L;

    private MockMvc mockMvc;

    @Mock
    private CaixaService service;

    @InjectMocks
    private CaixaController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenAnAporteIsCreated() throws Exception {
        CaixaDTO caixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();

        when(service.saveAporte(caixaDTO)).thenReturn("Aporte para " + caixaDTO.getCpf() + " cadastrado com sucesso!");

        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(caixaDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
        CaixaDTO caixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();

        when(service.findById(1L)).thenReturn(caixaDTO);

        mockMvc.perform(get(API_URL_PATH + 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf", is(caixaDTO.getCpf())))
                .andExpect(jsonPath("$.aporte", is(caixaDTO.getAporte())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {

        when(service.findById(INVALID_ID)).thenThrow(DataNotFoundException.class);

        mockMvc.perform(get(API_URL_PATH + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithAportesByCpfIsCalledThenOkStatusIsReturned() throws Exception {
        CaixaDTO caixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();

        when(service.findByCPF(caixaDTO.getCpf())).thenReturn(Collections.singletonList(caixaDTO));

        mockMvc.perform(get(API_URL_PATH + "cpf/" + caixaDTO.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cpf", is(caixaDTO.getCpf())))
                .andExpect(jsonPath("$[0].aporte", is(caixaDTO.getAporte())));
    }

    @Test
    void whenGETListWithoutAportesByCpfIsCalledThenOkStatusIsReturned() throws Exception {
        CaixaDTO caixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();

        when(service.findByCPF(caixaDTO.getCpf())).thenReturn(Collections.EMPTY_LIST);

        mockMvc.perform(get(API_URL_PATH + "cpf/" + caixaDTO.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenGETCalculateAposentadoriaByCpfThenOkStatusIsReturned() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.calculaAposentadoria(beneficiarioDTO.getCpf())).thenReturn(1111.111111111111);

        mockMvc.perform(get(API_URL_PATH + "calcula/" + beneficiarioDTO.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1111.111111111111)));
    }

    @Test
    void whenGETCalculateAposentadoriaIsCalledWithoutRegisteredCpfThenNotFoundStatusIsReturned() throws Exception {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(service.calculaAposentadoria(beneficiarioDTO.getCpf())).thenThrow(DataNotFoundException.class);

        mockMvc.perform(get(API_URL_PATH + "calcula/" + beneficiarioDTO.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
