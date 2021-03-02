package br.com.aposentadoria.services.service;

import br.com.aposentadoria.services.builder.BeneficiarioDTOBuilder;
import br.com.aposentadoria.services.dto.BeneficiarioDTO;
import br.com.aposentadoria.services.entity.Beneficiario;
import br.com.aposentadoria.services.exception.DataNotFoundException;
import br.com.aposentadoria.services.mapper.BeneficiarioMapper;
import br.com.aposentadoria.services.repository.BeneficiarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BeneficiarioServiceTest {

    @Mock
    private BeneficiarioRepository repo;

    private final BeneficiarioMapper mapper = BeneficiarioMapper.INSTANCE;

    @InjectMocks
    private BeneficiarioService service;

    @Test
    void whenBeneficiarioInformedThenItShouldBeCreated() {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();
        Beneficiario expectedSaved = mapper.toModel(beneficiarioDTO);

        when(repo.save(expectedSaved)).thenReturn(expectedSaved);

        String response = service.saveBeneficiario(beneficiarioDTO);

        assertThat(response, is(equalTo("Beneficiário " + expectedSaved.getNome() + " cadastrado com sucesso!")));
    }

    @Test
    void whenBeneficiarioInformedThenItShouldBeUpdated() throws DataNotFoundException {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();
        Beneficiario expectedUpdated = mapper.toModel(beneficiarioDTO);

        when(repo.findById(beneficiarioDTO.getId())).thenReturn(Optional.of(expectedUpdated));
        when(repo.save(expectedUpdated)).thenReturn(expectedUpdated);

        String response = service.updateBeneficiario(beneficiarioDTO.getId(), beneficiarioDTO);

        assertThat(response, is(equalTo("Beneficiário " + expectedUpdated.getNome() + " atualizado com sucesso!")));
    }

    @Test
    void whenNotRegisteredBeneficiarioInformedThenThrowAnException() {
        BeneficiarioDTO beneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(repo.findById(beneficiarioDTO.getId())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.updateBeneficiario(beneficiarioDTO.getId(), beneficiarioDTO));
    }

    @Test
    void whenListBeneficiarioIsCalledThenReturnAListOfBeneficiarios() {
        BeneficiarioDTO expectedFoundBeneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();
        Beneficiario expectedFound = mapper.toModel(expectedFoundBeneficiarioDTO);

        when(repo.findAll()).thenReturn(Collections.singletonList(expectedFound));

        List<BeneficiarioDTO> foundListBeneficiarioDTO = service.listAll();

        assertThat(foundListBeneficiarioDTO, is(not(empty())));
        assertThat(foundListBeneficiarioDTO.get(0), is(equalTo(expectedFoundBeneficiarioDTO)));
    }

    @Test
    void whenListBeneficiarioIsCalledThenReturnAnEmptyListOfBeneficiarios() {
        when(repo.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<BeneficiarioDTO> foundListBeneficiarioDTO = service.listAll();

        assertThat(foundListBeneficiarioDTO, is(empty()));
    }

    @Test
    void whenValidBeneficiarioIdIsGivenThenReturnABeneficiario() throws DataNotFoundException {
        BeneficiarioDTO expectedFoundBeneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();
        Beneficiario expectFound = mapper.toModel(expectedFoundBeneficiarioDTO);

        when(repo.findById(expectedFoundBeneficiarioDTO.getId())).thenReturn(Optional.of(expectFound));

        BeneficiarioDTO foundBeneficiarioDTO = service.findById(expectedFoundBeneficiarioDTO.getId());

        assertThat(foundBeneficiarioDTO, is(equalTo(expectedFoundBeneficiarioDTO)));
    }

    @Test
    void whenNotRegisteredBeneficiarioIdIsGivenThenThrowAnException() {
        BeneficiarioDTO expectedFoundBeneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(repo.findById(expectedFoundBeneficiarioDTO.getId())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.findById(expectedFoundBeneficiarioDTO.getId()));
    }

    @Test
    void whenValidBeneficiarioCpfIsGivenThenReturnABeneficiario() throws DataNotFoundException {
        BeneficiarioDTO expectedFoundBeneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();
        Beneficiario expectFound = mapper.toModel(expectedFoundBeneficiarioDTO);

        when(repo.findByCpf(expectedFoundBeneficiarioDTO.getCpf())).thenReturn(Optional.of(expectFound));

        BeneficiarioDTO foundBeneficiarioDTO = service.findByCPF(expectedFoundBeneficiarioDTO.getCpf());

        assertThat(foundBeneficiarioDTO, is(equalTo(expectedFoundBeneficiarioDTO)));
    }

    @Test
    void whenNotRegisteredBeneficiarioCpfIsGivenThenThrowAnException() {
        BeneficiarioDTO expectedFoundBeneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();

        when(repo.findByCpf(expectedFoundBeneficiarioDTO.getCpf())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.findByCPF(expectedFoundBeneficiarioDTO.getCpf()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABeneficiarioShouldBeDeleted() throws DataNotFoundException {
        BeneficiarioDTO expectedDeletedBeneficiarioDTO = BeneficiarioDTOBuilder.builder().build().toBeneficiarioDTO();
        Beneficiario expectedDeleted = mapper.toModel(expectedDeletedBeneficiarioDTO);

        when(repo.findById(expectedDeletedBeneficiarioDTO.getId())).thenReturn(Optional.of(expectedDeleted));
        doNothing().when(repo).deleteById(expectedDeletedBeneficiarioDTO.getId());

        service.delete(expectedDeletedBeneficiarioDTO.getId());

        verify(repo, times(1)).findById(expectedDeletedBeneficiarioDTO.getId());
        verify(repo, times(1)).deleteById(expectedDeletedBeneficiarioDTO.getId());
    }

    @Test
    void whenExclusionIsCalledWithInvalidIdThenExceptionShouldBeThrown() {
        when(repo.findById(10L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> service.delete(10L));
    }

}
