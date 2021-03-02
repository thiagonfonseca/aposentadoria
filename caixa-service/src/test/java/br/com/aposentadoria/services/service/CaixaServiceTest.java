package br.com.aposentadoria.services.service;

import br.com.aposentadoria.services.builder.BeneficiarioDTOBuilder;
import br.com.aposentadoria.services.builder.CaixaDTOBuilder;
import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.dto.CaixaDTO;
import br.com.aposentadoria.services.entity.Caixa;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.kafka.producer.Sender;
import br.com.aposentadoria.services.mapper.CaixaMapper;
import br.com.aposentadoria.services.repository.CaixaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CaixaServiceTest {

    @Mock
    private CaixaRepository repo;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Sender sender;

    private final CaixaMapper mapper = CaixaMapper.INSTANCE;

    @InjectMocks
    private CaixaService service;

    @Test
    void whenAporteInformedThenItShouldBeCreated() {
        CaixaDTO caixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();
        Caixa expectedSaved = mapper.toModel(caixaDTO);

        when(repo.save(expectedSaved)).thenReturn(expectedSaved);

        String response = service.saveAporte(caixaDTO);

        assertThat(response, is(equalTo("Aporte para " + expectedSaved.getCpf() + " cadastrado com sucesso!")));
    }

    @Test
    void whenValidAporteIdIsGivenThenReturnAnAporte() throws DataNotFoundException {
        CaixaDTO expectedFoundCaixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();
        Caixa expectFound = mapper.toModel(expectedFoundCaixaDTO);

        when(repo.findById(1L)).thenReturn(Optional.of(expectFound));

        CaixaDTO foundCaixaDTO = service.findById(1L);

        assertThat(foundCaixaDTO, is(equalTo(expectedFoundCaixaDTO)));
    }

    @Test
    void whenNotRegisteredAporteIdIsGivenThenThrowAnException() {
        CaixaDTO expectedFoundCaixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();

        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.findById(2L));
    }

    @Test
    void whenValidBeneficiarioCpfIsGivenThenReturnAListOfAportes() throws DataNotFoundException {
        CaixaDTO expectedFoundCaixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();
        Caixa expectFound = mapper.toModel(expectedFoundCaixaDTO);

        when(repo.findByCpf(expectedFoundCaixaDTO.getCpf())).thenReturn(Optional.of(Collections.singletonList(expectFound)));

        List<CaixaDTO> foundCaixaDTO = service.findByCPF(expectedFoundCaixaDTO.getCpf());

        assertThat(foundCaixaDTO.get(0), is(equalTo(expectedFoundCaixaDTO)));
    }

    @Test
    void whenNotRegisteredBeneficiarioCpfIsGivenThenThrowAnException() {
        CaixaDTO expectedFoundCaixaDTO = CaixaDTOBuilder.builder().build().toCaixaDTO();

        when(repo.findByCpf(expectedFoundCaixaDTO.getCpf())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.findByCPF(expectedFoundCaixaDTO.getCpf()));
    }

    @Test
    void whenCalculateAposentadoriaWithValidBeneficiarioCpfThenReturnAnResult() throws DataNotFoundException {
        BeneficiarioDTO expectedFoundBeneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(restTemplate.getForObject("http://localhost:9001/api/v1/beneficiarios/cpf/" + expectedFoundBeneficiarioDTO.getCpf(), BeneficiarioDTO.class))
                .thenReturn(expectedFoundBeneficiarioDTO);

        Double result = service.calculaAposentadoria(expectedFoundBeneficiarioDTO.getCpf());

        assertThat(result, is(equalTo(1111.111111111111)));
    }

    @Test
    void whenCalculateAposentadoriaWithNotRegisteredBeneficiarioCpfThenThrowAnException() {
        BeneficiarioDTO expectedFoundBeneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(restTemplate.getForObject("http://localhost:9001/api/v1/beneficiarios/cpf/" + expectedFoundBeneficiarioDTO.getCpf(), BeneficiarioDTO.class))
                .thenReturn(null);

        assertThrows(DataNotFoundException.class, () -> service.calculaAposentadoria(expectedFoundBeneficiarioDTO.getCpf()));
    }

}
